package com.codewithme.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.codewithme.blog.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor

@Setter
@Getter
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min = 4,message = "name should be 4 charectore")
	private String name;
	@NotEmpty
	@Size(min = 5, message = "password is must be of 5 charectore")
	private String password;
	@Email
	private String email;
	@NotNull
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
