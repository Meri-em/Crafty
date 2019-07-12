package com.crafty.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crafty.entity.OrderItem;
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
	private final MapperHelper mapperHelper;
	
	public OrderService(OrderRepository orderRepository,
						MapperHelper mapperHelper) {
		this.orderRepository = orderRepository;
		this.mapperHelper = mapperHelper;
	}
	
	public List<OrderDTO> getOrders(String memberId) {
		List<OrderDTO> result = new ArrayList<>();
		List<Order> orders = orderRepository.findByMemberId(memberId);
		for (Order order : orders) {
			BigDecimal totalPaid = getOrderTotalPaid(order);
			result.add(mapperHelper.toOrderDTO(order, totalPaid));
		}
		return result;
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
