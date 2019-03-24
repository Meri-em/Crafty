package com.crafty.web.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -3789107191526889575L;
	
	public BadRequestException(String message) {
		super(message);
	}

}
