package com.crafty.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CategoryItem implements Serializable {
	
	private static final long serialVersionUID = -5829980568864165960L;

	private String href;
	
	private String text;
	
	@JsonInclude(Include.NON_NULL)
	private List<CategoryItem> items;
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CategoryItem> getItems() {
		return items;
	}

	public void setItems(List<CategoryItem> items) {
		this.items = items;
	}
	
}
