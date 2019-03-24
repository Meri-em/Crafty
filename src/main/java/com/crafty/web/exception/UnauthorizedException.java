package com.crafty.web.exception;

public class UnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = -1221376840745754681L;

	public UnauthorizedException(String message) {
		super(message);
	}

}
