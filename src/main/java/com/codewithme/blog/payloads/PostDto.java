package com.codewithme.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer Id;
	private String title;
	private String content;
    private String image;
    private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();

	
}
