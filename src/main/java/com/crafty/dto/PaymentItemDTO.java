package com.crafty.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentItemDTO implements Serializable {
	
	private static final long serialVersionUID = 2716936980127872811L;
	
	private int quantity;
	
	private BigDecimal paidPerItem;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm.SSS")
	private LocalDateTime createdAt;
	
	private SimpleItemDTO item;

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public SimpleItemDTO getItem() {
		return item;
	}

	public void setItem(SimpleItemDTO item) {
		this.item = item;
	}
	
}
