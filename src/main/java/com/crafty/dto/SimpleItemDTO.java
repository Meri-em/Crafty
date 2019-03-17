package com.crafty.dto;

import java.io.Serializable;

public class SimpleItemDTO implements Serializable {
	
	private static final long serialVersionUID = -7110028552267411449L;
	
	private String id;
	
	private String name;
	
	private double price;
	
	private AuthorDTO author;
	
	private String primaryImagePath;
	
	public SimpleItemDTO () { }
	
	public SimpleItemDTO(String id, String name, double price, AuthorDTO author, String primaryImagePath) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.author = author;
		this.primaryImagePath = primaryImagePath;
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
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public String getPrimaryImagePath() {
		return primaryImagePath;
	}

	public void setPrimaryImagePath(String primaryImagePath) {
		this.primaryImagePath = primaryImagePath;
	}
	
	
	
}
