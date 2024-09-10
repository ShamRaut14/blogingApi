package com.codewithme.blog.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String fildName;
	Integer fildValue;
	public ResourceNotFoundException(String resourceName, String fildName, Integer fildValue) {
		super(String.format("%s not found with %s:%s", resourceName,fildName,fildValue));
		this.resourceName = resourceName;
		this.fildName = fildName;
		this.fildValue = fildValue;
	}
	
}
