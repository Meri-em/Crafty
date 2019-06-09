package com.crafty.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.CartItemDTO;
import com.crafty.entity.CartItem;
import com.crafty.entity.Item;
import com.crafty.repository.CartItemRepository;
import com.crafty.repository.ItemRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.NotFoundException;

@Service
@Transactional
public class CartService {
	
	private final ItemRepository itemRepository;
	private final CartItemRepository cartItemRepository;
	private final MapperHelper mapperHelper;
	
	public CartService(CartItemRepository cartItemRepository,
			ItemRepository itemRepository,
			MapperHelper mapperHelper) {
		this.cartItemRepository = cartItemRepository;
		this.itemRepository = itemRepository;
		this.mapperHelper = mapperHelper;
	}
	
	public List<CartItemDTO> getCartItems(String memberId) {
		return cartItemRepository.findByMemberId(memberId).stream()
				.map(ci -> mapperHelper.toCartItemDTO(ci)).collect(Collectors.toList());
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

}
