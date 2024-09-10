package com.codewithme.blog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithme.blog.Services.UserService;
import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.UserDto;

import jakarta.validation.Valid;
@RequestMapping("/api/users")
@RestController
public class UserController {
	
	@Autowired
	private UserService userservice;
		//create user
		@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
			
			
				UserDto createUserDto = this.userservice.createUser(userDto);
				return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
		}
		
		//Update user
		@PreAuthorize("hasRole('ADMIN')")
		@PutMapping("/{userId}")
	public ResponseEntity<UserDto> UpdateUser (@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
	
			UserDto updateUser = this.userservice.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updateUser);
				
	}
		//Delete user
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/{userId}")
		public ResponseEntity<ApiResponse> deletUser (@PathVariable Integer userId ){
			this.userservice.deleteUser(userId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Sucssesfully",true),HttpStatus.OK);
		}
		
		//Get User 
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getUser(@PathVariable Integer userId){
			
			return ResponseEntity.ok(this.userservice.getUserById(userId));
		}
		//Get all user
		@GetMapping("/")
		public ResponseEntity<List<UserDto>>getAllUsers(){
			return ResponseEntity.ok(this.userservice.getAllUsers());
		}
		
}
