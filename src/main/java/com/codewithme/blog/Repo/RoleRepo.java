package com.codewithme.blog.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithme.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
