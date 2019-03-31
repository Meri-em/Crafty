package com.crafty.dto;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
	
    private static final long serialVersionUID = 2442097946474456470L;

    private String error;


    /**
     * Default constructor so the class can be de-serialized from json
     */
    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String error) {
        super();
        this.error = error;
    }

   

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}

