package com.crafty.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crafty.dto.OrdersDTO;
import com.crafty.entity.Member;
import com.crafty.entity.OrderItem;
import com.crafty.repository.MemberRepository;
import org.springframework.stereotype.Service;

import com.crafty.dto.OrderDTO;
import com.crafty.dto.OrderItemDTO;
import com.crafty.entity.Order;
import com.crafty.repository.OrderRepository;
import com.crafty.util.MapperHelper;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final MemberService memberService;
	private final MapperHelper mapperHelper;
	
	public OrderService(OrderRepository orderRepository,
						MemberService memberService,
						MapperHelper mapperHelper) {
		this.orderRepository = orderRepository;
		this.memberService = memberService;
		this.mapperHelper = mapperHelper;
	}
	
	public OrdersDTO getOrders(String memberId) {
		List<Order> purchases = orderRepository.findByPurchaserMemberIdOrderByCreatedAtDesc(memberId);
		List<Order> sales = orderRepository.findBySellerMemberIdOrderByCreatedAtDesc(memberId);

		List<OrderDTO> purchasesDTO = purchases.stream()
			.map(p -> {
				BigDecimal totalPaid = getOrderTotalPaid(p);
				Member member = memberService.getMemberByIdOrNotFound(p.getSellerMemberId());
				return mapperHelper.toOrderDTO(p, totalPaid, member);
			}).collect(Collectors.toList());

		List<OrderDTO> salesDTO = sales.stream()
			.map(p -> {
				BigDecimal totalPaid = getOrderTotalPaid(p);
				Member member = memberService.getMemberByIdOrNotFound(p.getPurchaserMemberId());
				return mapperHelper.toOrderDTO(p, totalPaid, member);
			}).collect(Collectors.toList());
		return new OrdersDTO(purchasesDTO, salesDTO);
	}

	private BigDecimal getOrderTotalPaid(Order order) {
		List<OrderItem> items = order.getItems();
		BigDecimal totalPaid  = BigDecimal.ZERO;
		for (OrderItem item : items) {
			totalPaid = totalPaid.add(
				item.getPaidPerItem().multiply(BigDecimal.valueOf(item.getQuantity()))
			);
		}
		return totalPaid;
	}

}
