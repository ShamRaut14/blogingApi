package com.codewithme.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithme.blog.Services.FileService;
import com.codewithme.blog.Services.PostService;
import com.codewithme.blog.config.AppConstent;
import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.PostDto;
import com.codewithme.blog.payloads.PostResponse;

import jakarta.servlet.http.HttpServletResponse;
@RequestMapping("/api/")
@RestController
public class PostController {
	@Autowired 
	private PostService postServise;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
		//create post
	@PostMapping("/user/{UserId}/category/{categoryCId}/posts")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto, 
			                               @PathVariable Integer UserId,
			                               @PathVariable Integer categoryCId)
	{
		
		PostDto creatpost= this.postServise.createPost(postDto, UserId, categoryCId);
		return new ResponseEntity<PostDto>(creatpost,HttpStatus.CREATED);
	}
	
	
	
	//find by Category
	@GetMapping("/category/{categoryCId}/posts")
	public ResponseEntity<List<PostDto>>getBycategory(@PathVariable Integer categoryCId){
		
		List<PostDto> getpost = this.postServise.getPostsByCategory(categoryCId);
			
		return new ResponseEntity<List<PostDto>>(getpost, HttpStatus.OK);
	
	}
	@GetMapping("/user/{UserId}/posts")
	public ResponseEntity<List<PostDto>>getByUser(@PathVariable Integer UserId){
		List<PostDto> posts = this.postServise.getPostsByUser(UserId);
			
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getpostbypostId (@PathVariable Integer postId){
		 PostDto postDto =this.postServise.getPostById(postId);
		 return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getallpost (@RequestParam(value = "pageNumber", defaultValue=AppConstent.PAGE_NUMBET,required = false)Integer pageNumber
			                                        ,@RequestParam(value = "pageSize",defaultValue = AppConstent.PAGE_SIZE, required = false)Integer pageSize,
			                                        @RequestParam(value = "sortBy",defaultValue = AppConstent.SORT_BY, required = false)String sortBy,
			                                        @RequestParam(value = "sortDir",defaultValue = AppConstent.SORT_DIR, required = false)String sortDir
			                                        ){
		PostResponse postResponse =this.postServise.getAllpost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	
	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>updatepost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		
		PostDto updatedpost = this.postServise.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedpost,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletepost( @PathVariable Integer postId){
		
		this.postServise.deletPost(postId);
		
		return new ApiResponse("Post is Deleted Sucsessfully",true);
	}
	
	//search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> search(@PathVariable("keyword") String keyword){
		
		List<PostDto> result=this.postServise.searchPost(keyword);
		
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}

	//post image uploads
	@PostMapping("/post/image/upload/{Id}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")
								MultipartFile image,
								@PathVariable Integer Id) throws IOException{
		PostDto postDto =this.postServise.getPostById(Id);
		String fileName= this.fileService.uploadImage(path, image);
		System.out.println(fileName);
		postDto.setImage(fileName);
		PostDto updatePost = this.postServise.updatePost(postDto, Id);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	//method to serve file
	@GetMapping(value = "post/image/{imageName}",produces= MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
