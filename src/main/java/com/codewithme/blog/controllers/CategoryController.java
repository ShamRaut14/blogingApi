package com.codewithme.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithme.blog.Services.CategoryService;
import com.codewithme.blog.payloads.ApiResponse;
import com.codewithme.blog.payloads.CategoryDto;

import jakarta.validation.Valid;


@RequestMapping("/api/category")
@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	//create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto CatDto) {
			
			
				CategoryDto createCategory = this.categoryService.createcategory(CatDto);
				return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
		}
	//update Category
	@PutMapping("/{Cid}")
	public ResponseEntity<CategoryDto> UpdateCategory( @Valid @RequestBody CategoryDto catDto,@PathVariable Integer Cid) {
			
				CategoryDto UpdateCategory = this.categoryService.updatecategory(catDto, Cid);
				return new ResponseEntity<>(UpdateCategory,HttpStatus.CREATED);
	}
	// delete Category
	@DeleteMapping("/{Cid}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer Cid ){
		this.categoryService.deletecategory(Cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Sucsessfully !!",true),HttpStatus.OK);
		
	}
	 
	// Get category
	@GetMapping("/{Cid}")
	public ResponseEntity<CategoryDto> getCategory (@PathVariable Integer Cid ){
		
		CategoryDto CatDto = this.categoryService.getcategory(Cid);
		
		
		return new ResponseEntity<CategoryDto>(CatDto, HttpStatus.OK);
	}
	
	//get All Category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories (){
		
		List<CategoryDto> CatDto = this.categoryService.getAllcategory();
		
		
		return  ResponseEntity.ok(CatDto);
	}
}
