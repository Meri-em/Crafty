package com.crafty.dto;

import java.io.Serializable;

public class FavoriteRequestDTO implements Serializable {
	
	private static final long serialVersionUID = -1847760977078822577L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
