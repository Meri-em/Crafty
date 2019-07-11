package com.crafty.entity;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.crafty.entity.base.BaseEntityId;

@Entity
public class Payment extends BaseEntityId {
	
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
	
	private BigDecimal paidPerItem;
	
	@NotNull
	private Instant createdAt;

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

	public BigDecimal getPaidPerItem() {
		return paidPerItem;
	}

	public void setPaidPerItem(BigDecimal paidPerItem) {
		this.paidPerItem = paidPerItem;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
}