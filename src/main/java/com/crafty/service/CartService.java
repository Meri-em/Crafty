package com.crafty.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crafty.dto.CartDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.CartItemDTO;
import com.crafty.entity.CartItem;
import com.crafty.entity.Item;
import com.crafty.entity.Payment;
import com.crafty.repository.CartItemRepository;
import com.crafty.repository.ItemRepository;
import com.crafty.repository.PaymentRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.NotFoundException;

@Service
@Transactional
public class CartService {
	
	private final ItemRepository itemRepository;
	private final CartItemRepository cartItemRepository;
	private final PaymentRepository paymentRepository;
	private final MapperHelper mapperHelper;
	
	public CartService(CartItemRepository cartItemRepository,
			ItemRepository itemRepository,
			PaymentRepository paymentRepository,
			MapperHelper mapperHelper) {
		this.cartItemRepository = cartItemRepository;
		this.itemRepository = itemRepository;
		this.paymentRepository = paymentRepository;
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
	
	public String addItemToCart(String memberId, String itemId, int quantity) {
		Optional<CartItem> cartItemOptional =  cartItemRepository.findByMemberIdAndItemId(memberId, itemId);
		CartItem cartItem = null;
		if (cartItemOptional.isPresent()) {
			cartItem = cartItemOptional.get();
		} else {
			cartItem = new CartItem();
			Item item = itemRepository.findById(itemId)
					.orElseThrow(() -> new NotFoundException("No item found with id " + itemId));
			cartItem.setItem(item);
			cartItem.setMemberId(memberId);
		}
		cartItem.setQuantity(quantity);
		cartItem = cartItemRepository.save(cartItem);
		return "Item saved";
	}
	
	public String modifyCartItem(String memberId, String itemId, int quantity) {
		CartItem cartItem =  cartItemRepository.findByMemberIdAndItemId(memberId, itemId)
				.orElseThrow(() -> new NotFoundException("No item with id " + itemId + " found for this user"));
		cartItem.setQuantity(quantity);
		cartItem = cartItemRepository.save(cartItem);
		return cartItem.getId();
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
		Instant createdAt = Instant.now();
		List<Payment> payments = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			Payment payment = new Payment();
			payment.setCreatedAt(createdAt);
			payment.setMemberId(memberId);
			Item item = cartItem.getItem();
			payment.setItem(item);
			payment.setPaidPerItem(item.getPrice());
			payment.setQuantity(cartItem.getQuantity());
			payments.add(payment);
		}
		payments = paymentRepository.saveAll(payments);
		cartItemRepository.deleteAll(cartItems);
	}

}
