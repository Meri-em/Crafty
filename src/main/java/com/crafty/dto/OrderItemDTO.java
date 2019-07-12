package com.crafty.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemDTO implements Serializable {
	
	private static final long serialVersionUID = 2716936980127872811L;

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	private int quantity;
	
	private BigDecimal paid;
	
	private SimpleItemDTO item;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public SimpleItemDTO getItem() {
		return item;
	}

	public void setItem(SimpleItemDTO item) {
		this.item = item;
	}
	
}
