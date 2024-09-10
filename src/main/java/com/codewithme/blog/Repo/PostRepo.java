package com.codewithme.blog.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithme.blog.entity.Category;
import com.codewithme.blog.entity.Post;
import com.codewithme.blog.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
		List<Post> findByTitleContaining(String title);
}
