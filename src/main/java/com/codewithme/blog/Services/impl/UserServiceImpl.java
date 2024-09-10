package com.codewithme.blog.Services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.usertype.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithme.blog.exception.*;
import com.codewithme.blog.Repo.RoleRepo;
import com.codewithme.blog.Repo.UserRepo;
import com.codewithme.blog.Services.UserService;
import com.codewithme.blog.config.AppConstent;
import com.codewithme.blog.entity.Role;
import com.codewithme.blog.entity.User;
import com.codewithme.blog.payloads.UserDto;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo usrRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User saveduser = this.usrRepo.save(user);
		
		return this.userToDto(saveduser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	
		User user = this.usrRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updateUser = this.usrRepo.save(user);
		UserDto userDto1 = this.userToDto(updateUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.usrRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.usrRepo.findAll();
		List<UserDto>userDtos = users.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer UserId) {
	
		User user= this.usrRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User","Id",UserId));
		
		this.usrRepo.delete(user);
	}
     
	public User dtoToUser (UserDto userDto) {
		User user = this.modelmapper.map(userDto,User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	public UserDto userToDto(User user) {
		
		UserDto userDto = this.modelmapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
	 User user =  this.modelmapper.map(userDto, User.class);
	         
	 user.setPassword(this.passwordEncoder.encode(user.getPassword()));
	 
	    Role role = this.roleRepo.findById(AppConstent.NORMAL_USER).get();   
	           
	    user.getRoles().add(role);
	    User newUser = this.usrRepo.save(user);
	    
		return this.modelmapper.map(newUser, UserDto.class);
	}
}
