package com.codewithme.blog.Services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithme.blog.Repo.CategoryRepo;
import com.codewithme.blog.Repo.PostRepo;
import com.codewithme.blog.Repo.UserRepo;
import com.codewithme.blog.Services.PostService;
import com.codewithme.blog.entity.Category;
import com.codewithme.blog.entity.Post;
import com.codewithme.blog.entity.User;
import com.codewithme.blog.exception.ResourceNotFoundException;
import com.codewithme.blog.payloads.PostDto;
import com.codewithme.blog.payloads.PostResponse;
@Service
public class PostServiceImpl implements PostService{

	     @Autowired
		private PostRepo postRepo;
	     
	     @Autowired
	    private ModelMapper modelMapper;
	     @Autowired
	     private UserRepo userRepo;
	     @Autowired
	     private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer UserId,Integer categoryCId) {
		User user =this.userRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User","UserId",UserId));
		Category category = this.categoryRepo.findById(categoryCId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryCId));
		
		Post post = this.modelMapper.map(postDto,Post.class);
		post.setImage("defoult.png");
		post.setAddedDate( new Date());
		post.setUser(user);
		post.setCategory(category);
		 
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}
	
	//update post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId));
         post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
	    Post  updatedpost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletPost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId));
		this.postRepo.delete(post);
		
		
		
	}

	@Override
	public PostResponse getAllpost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort= null;
		if(sortDir.equalsIgnoreCase("1")) {
			sort=Sort.by(sortBy).ascending();
		}else  {
			sort= Sort.by(sortBy).descending();
		}
		
		Pageable p =PageRequest.of(pageNumber, pageSize,sort);
		Page<Post>pagePost= this.postRepo.findAll(p);
		List<Post> getAll =pagePost.getContent();
		List<PostDto>postDto = getAll.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse =new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
		
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryCId) {
		
		Category cat = this.categoryRepo.findById(categoryCId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryCId));
		List<Post> posts =this.postRepo.findByCategory(cat);
		List<PostDto>postDto = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
				
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer UserId) {
		User user = this.userRepo.findById(UserId).orElseThrow(()->new ResourceNotFoundException("user","UserId",UserId));
		List<Post> posts =this.postRepo.findByUser(user);
		List<PostDto>postDto = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
				
		return postDto;
	}

	@Override
	public List<PostDto> searchPost(String Keyword) {
		List<Post>result=this.postRepo.findByTitleContaining(Keyword);
		List<PostDto>postDtos=result.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	

}
