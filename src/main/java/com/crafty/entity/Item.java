package com.crafty.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class Item extends BaseEntityId {

	private static final long serialVersionUID = -5957580020187573652L;
	
	@NotNull
	@Column(columnDefinition = "varchar(100)")
	private String name;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@Column(columnDefinition = "varchar(100)")
	private String category;
	
	@NotNull
	private Instant createdAt;
	
	private double price;
	
	@ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
	private Author author;
	
	@OneToMany(mappedBy = "item", fetch=FetchType.EAGER)
	private List<ItemImage> itemImages = new ArrayList<>();

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<ItemImage> getItemImages() {
		return itemImages;
	}

	public void setItemImages(List<ItemImage> itemImages) {
		this.itemImages = itemImages;
	}
	
	
}
