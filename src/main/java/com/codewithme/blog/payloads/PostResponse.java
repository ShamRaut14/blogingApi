package com.codewithme.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	
	private List<PostDto> content;
	
	private int pageNumber;
	private int PageSize;
	
	private long totalElement;
	
	private int totalpages;
	
	private boolean lastPage;
	
}
