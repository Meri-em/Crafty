package com.crafty.web.exception;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 8942613512090742154L;

	public ForbiddenException(String message) {
		super(message);
	}
}
