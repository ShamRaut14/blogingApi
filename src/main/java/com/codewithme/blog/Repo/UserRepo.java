package com.codewithme.blog.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithme.blog.entity.User;

public interface UserRepo extends JpaRepository <User, Integer>{

	
	Optional<User> findByEmail(String email);
}
