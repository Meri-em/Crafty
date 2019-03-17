package com.crafty.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class Author extends BaseEntityId {
	
	private static final long serialVersionUID = 4889035447004890771L;
	
	private String name;
	
	@OneToMany(mappedBy = "author")
	private List<Item> items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
