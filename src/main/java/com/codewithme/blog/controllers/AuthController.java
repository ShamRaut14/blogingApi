package com.codewithme.blog.controllers;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithme.blog.Services.UserService;
import com.codewithme.blog.exception.ApiException;
import com.codewithme.blog.payloads.LoginRequest;
import com.codewithme.blog.payloads.LoginResponse;
import com.codewithme.blog.payloads.UserDto;
import com.codewithme.blog.security.JWTtokenHelpper;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	@Autowired
	private JWTtokenHelpper jWTtokenHelpper;
	@Autowired
	private UserDetailsService userDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> creatToken(@RequestBody LoginRequest request) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails= this.userDetailService.loadUserByUsername(request.getUsername());
		
		String token= this.jWTtokenHelpper.generateToken(userDetails);
		
		LoginResponse response = new LoginResponse(token);
		response.setToken(token);
		
		return new ResponseEntity<LoginResponse>(response,HttpStatus.OK);
	}
	
	private void authenticate(String username,String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username,password);
			
	try {
		this.authenticationManager.authenticate(authenticationToken);
	}catch(BadCredentialsException e) {
		System.out.println("invalid details !!");
		throw new ApiException("invalid username or password");
	}
	}
	
	@PostMapping("/resister")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto registerUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
		
	}
}
