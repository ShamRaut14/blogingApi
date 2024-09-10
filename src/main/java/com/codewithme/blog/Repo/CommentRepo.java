package com.codewithme.blog.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithme.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{

}
