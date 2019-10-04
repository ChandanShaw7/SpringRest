package com.stackrest.springrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	private String userName;
	
	public ResourceNotFoundException(String fieldName,String fieldValue) {
		super(String.format("user not found with %s : '%s'", fieldName, fieldValue));
		this.userName = fieldValue;
	}
	public String getUserName() {
		return userName;
	}
}

