package com.crafty.dto;

import java.io.Serializable;
import java.util.List;

public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = 4109910896425338737L;
	
	private double totalPaid;
	
	private List<PaymentItemDTO> payments;

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public List<PaymentItemDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentItemDTO> payments) {
		this.payments = payments;
	}
}
