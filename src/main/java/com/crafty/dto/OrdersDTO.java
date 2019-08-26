package com.crafty.dto;

import java.io.Serializable;
import java.util.List;

public class OrdersDTO implements Serializable {

	private static final long serialVersionUID = 6666478938183967857L;

	private List<OrderDTO> purchases;

	private List<OrderDTO> sales;

	public OrdersDTO(List<OrderDTO> purchases, List<OrderDTO> sales) {
		this.purchases = purchases;
		this.sales = sales;
	}

	public List<OrderDTO> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<OrderDTO> purchases) {
		this.purchases = purchases;
	}

	public List<OrderDTO> getSales() {
		return sales;
	}

	public void setSales(List<OrderDTO> sales) {
		this.sales = sales;
	}
}
