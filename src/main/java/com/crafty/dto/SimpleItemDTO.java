package com.crafty.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SimpleItemDTO implements Serializable {
	
	private static final long serialVersionUID = -7110028552267411449L;
	
	private String id;
	
	private String name;
	
	private BigDecimal price;

	private String category;
	
	private MemberDTO author;
	
	private String image;

	private boolean archived;
	
	public SimpleItemDTO () { }
	
	public SimpleItemDTO(String id, String name, BigDecimal price, String category, MemberDTO author,
						 String image, boolean archived) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.author = author;
		this.image = image;
		this.archived = archived;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public MemberDTO getAuthor() {
		return author;
	}

	public void setAuthor(MemberDTO author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean getArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
}
