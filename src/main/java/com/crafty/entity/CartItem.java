package com.crafty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class CartItem extends BaseEntityId {
	
	private static final long serialVersionUID = -5553205123797954099L;

	@JoinColumn(name = "member_id")
	@NotNull
	@Column(columnDefinition = "CHAR(255)")
	private String memberId;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	@NotNull
	private Item item;
	
	private int quantity;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
