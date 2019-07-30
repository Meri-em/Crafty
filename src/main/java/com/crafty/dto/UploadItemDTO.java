package com.crafty.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

public class UploadItemDTO implements Serializable {

	private static final long serialVersionUID = 2798125379475182338L;

	@NotBlank
	private String name;

	private String description;

	private BigDecimal price;

	private String category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
