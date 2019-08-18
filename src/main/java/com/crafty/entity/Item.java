package com.crafty.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
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
	
	private BigDecimal price;
	
	@ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
	private Member member;
	
	@OneToMany(mappedBy = "item", fetch=FetchType.EAGER, cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<ItemImage> itemImages = new ArrayList<>();
	
	@Column(columnDefinition = "BIT(1)")
	private boolean archived;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<ItemImage> getItemImages() {
		return itemImages;
	}

	public void setItemImages(List<ItemImage> itemImages) {
		this.itemImages = itemImages;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
}
