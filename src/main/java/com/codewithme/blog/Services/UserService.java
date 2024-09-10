package com.codewithme.blog.Services;

import java.util.List;

import com.codewithme.blog.payloads.UserDto;

public interface UserService {
		
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);                  // this methode is for creating the user
	UserDto updateUser(UserDto user, Integer userId);  // this mathode is for updating the user 
	UserDto getUserById(Integer userId);               //  to get the user by Id
	List<UserDto>getAllUsers();                        // we use list becous we want all user all user get store in list and then we use it
	void deleteUser (Integer UserId);                  // we use void becouse it will not return anything 
	
}
