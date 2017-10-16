package com.pk.love.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8755612014987143581L;
	public NotFoundException(){
		super();
	}
	public NotFoundException(String message){
		super(message);
	}
	public NotFoundException(String message, Throwable cause){
		super(message, cause);
	}
}
