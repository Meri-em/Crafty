package com.crafty.web.rest;

import com.crafty.util.CurrentUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.PaymentDTO;
import com.crafty.security.JwtUser;
import com.crafty.service.PaymentService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/payments")
public class PaymentResource {
	
	private final PaymentService paymentService;
	
	public PaymentResource(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("")
	public PaymentDTO getPayments() {
		return paymentService.getPayments(CurrentUser.getMemberId());
	}

}
