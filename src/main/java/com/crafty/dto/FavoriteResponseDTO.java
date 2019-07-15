package com.crafty.dto;

import java.io.Serializable;

public class FavoriteResponseDTO implements Serializable {
	
	private static final long serialVersionUID = -4477303140302930634L;

	private String id;
	
	private String name;

	public FavoriteResponseDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
