package com.codewithme.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithme.blog.Services.CommentService;
import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.CommentDto;

@RequestMapping("/Api/")
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		
		CommentDto com= this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(com,HttpStatus.CREATED);
	}
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity <ApiResponse> deletecomment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted sucsessfully",true),HttpStatus.OK);
	}
}
