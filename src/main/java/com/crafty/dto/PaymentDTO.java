package com.crafty.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = 4109910896425338737L;
	
	private BigDecimal totalPaid;
	
	private List<PaymentItemDTO> payments;

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public List<PaymentItemDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentItemDTO> payments) {
		this.payments = payments;
	}
}
