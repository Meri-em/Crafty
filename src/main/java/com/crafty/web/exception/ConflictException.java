package com.crafty.web.exception;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = -8750434631700437656L;
	
	public ConflictException(String message) {
		super(message);
	}

}
