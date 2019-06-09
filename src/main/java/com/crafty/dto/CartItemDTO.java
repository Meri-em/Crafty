package com.crafty.dto;

import java.io.Serializable;

public class CartItemDTO implements Serializable {

	private static final long serialVersionUID = 5122903898514337029L;
	
	private int quantity;
	
	private SimpleItemDTO simpleItem;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public SimpleItemDTO getSimpleItem() {
		return simpleItem;
	}

	public void setSimpleItem(SimpleItemDTO simpleItem) {
		this.simpleItem = simpleItem;
	}
	
}
