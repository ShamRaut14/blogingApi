package com.codewithme.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	
	private Integer CId;
	
	@NotEmpty
	@Size(min = 4, message = "minimum size is 4")
	private String CTitle;
	@NotEmpty
	@Size(min = 5, message = "minimum size is 5")
	private String CDescription;
}
