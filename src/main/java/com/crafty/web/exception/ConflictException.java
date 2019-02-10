package com.crafty.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = -8750434631700437656L;
	
	public ConflictException(String message) {
		super(message);
	}

}
