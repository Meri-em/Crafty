package com.crafty.web.exception;

public class UnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = 8969565878949884354L;

    public UnprocessableEntityException(String message) {
        super(message);
    }

}

