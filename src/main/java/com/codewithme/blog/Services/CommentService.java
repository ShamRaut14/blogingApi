package com.codewithme.blog.Services;
import org.springframework.stereotype.Service;

import com.codewithme.blog.payloads.CommentDto;
@Service
public interface CommentService {

	public CommentDto createComment (CommentDto commentDto, Integer postId);
	  void deleteComment(Integer commentId);
}
