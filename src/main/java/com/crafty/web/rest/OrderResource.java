package com.crafty.web.rest;

import com.crafty.dto.OrdersDTO;
import com.crafty.util.CurrentUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/orders")
public class OrderResource {
	
	private final OrderService orderService;
	
	public OrderResource(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("")
	public OrdersDTO getOrders() {
		return orderService.getOrders(CurrentUser.getMemberId());
	}

}
