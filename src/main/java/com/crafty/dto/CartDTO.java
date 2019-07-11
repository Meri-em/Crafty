package com.crafty.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartDTO implements Serializable {

	private static final long serialVersionUID = -5207211616904788152L;

	public CartDTO(BigDecimal total, List<CartItemDTO> items) {
		this.total = total;
		this.items = items;
	}

	private BigDecimal total;

	private List<CartItemDTO> items;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	}
}
