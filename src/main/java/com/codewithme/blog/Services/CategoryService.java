package com.codewithme.blog.Services;

import java.util.List;

import com.codewithme.blog.payloads.CategoryDto;

public interface CategoryService {
		
		//create category
	public CategoryDto createcategory(CategoryDto categoryDto);
	
		//update category
	public CategoryDto updatecategory(CategoryDto categoryDto, Integer Cid);
		
		//Delete 
	public void deletecategory(Integer Cid);
	
	//Get
	public CategoryDto getcategory( Integer Cid);
	
		//Get All
	List<CategoryDto> getAllcategory();
	
}
