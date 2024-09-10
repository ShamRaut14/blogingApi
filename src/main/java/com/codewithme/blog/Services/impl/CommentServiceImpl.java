package com.codewithme.blog.Services.impl;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithme.blog.Repo.CommentRepo;
import com.codewithme.blog.Repo.PostRepo;
import com.codewithme.blog.Services.CommentService;
import com.codewithme.blog.entity.Comment;
import com.codewithme.blog.entity.Post;
import com.codewithme.blog.exception.ResourceNotFoundException;
import com.codewithme.blog.payloads.CommentDto;
@Service
public class CommentServiceImpl implements CommentService  {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
	Comment comment	 = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("cooment","comment id",commentId));
		
		this.commentRepo.delete(comment);
	}
      
	
}
