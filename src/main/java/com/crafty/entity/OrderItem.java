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
public class OrderItem extends BaseEntityId {

	private static final long serialVersionUID = -5553205123797954099L;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@NotNull
	private Order order;

	@ManyToOne
	@JoinColumn(name = "item_id")
	@NotNull
	private Item item;

	private int quantity;

	private BigDecimal paidPerItem;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

}
