package com.crafty.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SimpleItemDTO implements Serializable {
	
	private static final long serialVersionUID = -7110028552267411449L;
	
	private String id;
	
	private String name;
	
	private BigDecimal price;
	
	private AuthorDTO author;
	
	private String image;
	
	public SimpleItemDTO () { }
	
	public SimpleItemDTO(String id, String name, BigDecimal price, AuthorDTO author, String image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.author = author;
		this.image = image;
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
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
