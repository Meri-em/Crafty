package com.crafty.web.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 635566719092278399L;
	
	public NotFoundException(String message) {
		super(message);
	}

}
