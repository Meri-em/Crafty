package com.crafty.config;

import java.io.Serializable;

public class ItemImageDTO implements Serializable {

	private static final long serialVersionUID = 2371282516925556806L;

	private String id;
	
	private String path;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
