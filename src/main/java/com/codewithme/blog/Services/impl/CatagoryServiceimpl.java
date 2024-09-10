package com.codewithme.blog.Services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithme.blog.Repo.CategoryRepo;
import com.codewithme.blog.Services.CategoryService;
import com.codewithme.blog.entity.Category;
import com.codewithme.blog.exception.ResourceNotFoundException;
import com.codewithme.blog.payloads.CategoryDto;
@Service
public class CatagoryServiceimpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createcategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedcat,CategoryDto.class);
	}

	@Override
	public CategoryDto updatecategory(CategoryDto categoryDto, Integer Cid) {
		Category cat = this.categoryRepo.findById(Cid).orElseThrow(()-> new ResourceNotFoundException("Category","Categoryid",Cid));
		
		cat.setCTitle(categoryDto.getCTitle());
		cat.setCDescription(categoryDto.getCDescription());
		Category updated =this.categoryRepo.save(cat);
		return this.modelMapper.map(updated,CategoryDto.class);
	}

	@Override
	public void deletecategory(Integer Cid) {
		
		Category cat = this.categoryRepo.findById(Cid).orElseThrow(()->new ResourceNotFoundException("Category","Categoryid",Cid));
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDto getcategory(Integer Cid) {
		Category cat = this.categoryRepo.findById(Cid).orElseThrow(()->new ResourceNotFoundException("Category","Categoryid",Cid));
		
		return this.modelMapper.map(cat,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllcategory() {
		List<Category> Categories = this.categoryRepo.findAll();
		List<CategoryDto> catDto= Categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catDto ;
	}

}
