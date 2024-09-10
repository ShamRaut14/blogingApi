package com.codewithme.blog.Services;

import java.util.List;

import com.codewithme.blog.entity.Post;
import com.codewithme.blog.payloads.PostDto;
import com.codewithme.blog.payloads.PostResponse;

public interface PostService {
    
		PostDto createPost(PostDto postDto,Integer UserId,Integer categoryCId);
		
		//update post
		PostDto updatePost(PostDto postDto, Integer postId);
		
		//delete Post 
		void deletPost (Integer postId);
		
		//get all post
		PostResponse getAllpost(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);
		
		//get post
		PostDto getPostById(Integer postId);
		
		//get all post by category
		List<PostDto> getPostsByCategory(Integer categoryCId);
		
		//gell all post by User
		List<PostDto> getPostsByUser(Integer UserId);
		
		//search post
		List<PostDto> searchPost(String Keyword);
		
}
