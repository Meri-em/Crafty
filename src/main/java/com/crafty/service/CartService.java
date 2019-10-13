package com.crafty.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crafty.dto.CartDTO;
import com.crafty.entity.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.CartItemDTO;
import com.crafty.entity.CartItem;
import com.crafty.entity.Item;
import com.crafty.entity.Order;
import com.crafty.repository.CartItemRepository;
import com.crafty.repository.ItemRepository;
import com.crafty.repository.OrderRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.NotFoundException;

@Service
@Transactional
public class CartService {
	
	private final ItemRepository itemRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderRepository orderRepository;
	private final MapperHelper mapperHelper;
	
	public CartService(CartItemRepository cartItemRepository,
			ItemRepository itemRepository,
			OrderRepository orderRepository,
			MapperHelper mapperHelper) {
		this.cartItemRepository = cartItemRepository;
		this.itemRepository = itemRepository;
		this.orderRepository = orderRepository;
		this.mapperHelper = mapperHelper;
	}
	
	public CartDTO getCartItems(String memberId) {
		List<CartItem> items = cartItemRepository.findByMemberId(memberId);
		List<CartItemDTO> itemDTOs = items.stream()
				.map(ci -> mapperHelper.toCartItemDTO(ci)).collect(Collectors.toList());
		BigDecimal total = BigDecimal.ZERO;
		for (CartItem item : items) {
			total = total.add(
				item.getItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		}
		return new CartDTO(total, itemDTOs);
	}
	
	public String createOrUpdateCartItem(String memberId, String itemId, String quantityString) {
		Optional<CartItem> cartItemOptional =  cartItemRepository.findByMemberIdAndItemId(memberId, itemId);
		CartItem cartItem = null;
		int savedQuantity = 0;
		if (cartItemOptional.isPresent()) {
			cartItem = cartItemOptional.get();
			savedQuantity = cartItem.getQuantity();
		} else {
			cartItem = new CartItem();
			Item item = itemRepository.findById(itemId)
					.orElseThrow(() -> new NotFoundException("No item found with id " + itemId));
			cartItem.setItem(item);
			cartItem.setMemberId(memberId);
		}
		int newQuantity = calculateNewQuantity(savedQuantity, quantityString);
		if (newQuantity > 0) {
			if (savedQuantity == newQuantity) {
				return String.format("Item with id %s and quantity %s already exists",
					itemId, savedQuantity);
			}
			cartItem.setQuantity(newQuantity);
			cartItem = cartItemRepository.save(cartItem);
			return "Успешно добавено в количка";
		} else if (savedQuantity != 0) {
			cartItemRepository.delete(cartItem);
			return "Item deleted";
		}
		return "Item not added because quantity is non-positive";
	}
	
	public void deleteItems(String memberId, List<String> itemIds) {
		cartItemRepository.deleteByMemberIdAndItemIdIn(memberId, itemIds);
	}
	
	public void clearCart(String memberId) {
		cartItemRepository.deleteByMemberId(memberId);
	}
	
	public void purchaseItemsFromCart(String memberId) {
		List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);
		if (cartItems.isEmpty()) {
			throw new BadRequestException("The cart is empty");
		}
		// the number of orders is equal to the number of different authors created the items
		List<Order> orders = new ArrayList<>();
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			Item item = cartItem.getItem();
			orderItem.setItem(item);
			orderItem.setPaidPerItem(item.getPrice());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setOrder(findOrderforMember(orders, memberId, item.getMember().getId(), orderItem));
			orderItems.add(orderItem);
		}
		orders = orderRepository.saveAll(orders);
		cartItemRepository.deleteAll(cartItems);
	}

	private Order findOrderforMember(List<Order> orders, String memberId, String sellerMemberId, OrderItem orderItem) {
		Order order = null;
		Optional<Order> orderOptional = orders.stream()
			.filter(or -> or.getSellerMemberId() != null && or.getSellerMemberId().equals(sellerMemberId)).findFirst();
		if (orderOptional.isPresent()) {
			order = orderOptional.get();
		} else {
			order = new Order();
			order.setPurchaserMemberId(memberId);
			order.setSellerMemberId(sellerMemberId);
			order.setCreatedAt(Instant.now());
			orders.add(order);

		}
		List<OrderItem> orderItems = order.getItems();
		orderItems.add(orderItem);
		return order;
	}

	private int calculateNewQuantity(int savedQuantity, String quantityString) {
		if (quantityString.startsWith("+")) {
			int counter = Integer.valueOf(quantityString.substring(1));
			return savedQuantity + counter;
		} else if (quantityString.startsWith("-")) {
			int counter = Integer.valueOf(quantityString.substring(1));
			return savedQuantity - counter;
		}
		return Integer.valueOf(quantityString);
	}

}
