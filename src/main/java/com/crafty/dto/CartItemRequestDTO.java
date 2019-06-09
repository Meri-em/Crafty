package com.crafty.dto;

import java.io.Serializable;

public class CartItemRequestDTO implements Serializable {

	private static final long serialVersionUID = 138990631819177471L;
	
	private String itemId;
	
	private int quantity;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
