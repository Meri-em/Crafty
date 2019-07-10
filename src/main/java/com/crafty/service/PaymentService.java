package com.crafty.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.crafty.dto.PaymentDTO;
import com.crafty.dto.PaymentItemDTO;
import com.crafty.entity.Payment;
import com.crafty.repository.PaymentRepository;
import com.crafty.util.MapperHelper;

@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final MapperHelper mapperHelper;
	
	public PaymentService(PaymentRepository paymentRepository,
			MapperHelper mapperHelper) {
		this.paymentRepository = paymentRepository;
		this.mapperHelper = mapperHelper;
	}
	
	public PaymentDTO getPayments(String memberId) {
		List<Payment> payments = paymentRepository.findByMemberId(memberId);
		BigDecimal totalPaid  = BigDecimal.ZERO;
		for (Payment payment : payments) {
			totalPaid = totalPaid.add(
				payment.getPaidPerItem().multiply(BigDecimal.valueOf(payment.getQuantity()))
			);
		}
		PaymentDTO result = new PaymentDTO();
		result.setTotalPaid(totalPaid);
		List<PaymentItemDTO> paymentItemDTO = payments.stream()
				.map(p -> mapperHelper.toPaymentItemDTO(p))
				.collect(Collectors.toList());
		result.setPayments(paymentItemDTO);
		return result;
	}

}
