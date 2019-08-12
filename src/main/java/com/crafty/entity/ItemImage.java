package com.crafty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class ItemImage extends BaseEntityId {

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(50)")
	private String extension;
	
	@Column(name = "`order`")
	private int order;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	@NotNull
	private Item item;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getName() {
		return String.format("%s.%s", getId(), getExtension());
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
