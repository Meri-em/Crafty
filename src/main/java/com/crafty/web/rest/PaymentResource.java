package com.crafty.web.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/payments")
public class PaymentResource {
	
	@GetMapping("")
	public void getPayments() {
		
	}
	
	@PostMapping("")
	public void addPayment() {
		
	}

}
