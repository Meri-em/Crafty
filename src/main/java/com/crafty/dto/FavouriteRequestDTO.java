package com.crafty.dto;

import java.io.Serializable;

public class FavouriteRequestDTO implements Serializable {
	
	private static final long serialVersionUID = -1847760977078822577L;
	
	private String authorId;

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	
}
