package com.crafty.web.rest;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.CartItemDTO;
import com.crafty.dto.CartItemRequestDTO;
import com.crafty.security.JwtUser;
import com.crafty.service.CartService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/cart")
public class CartResource {
	
	private final CartService cartService;
	
	public CartResource(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping("")
	public List<CartItemDTO> getCartItems() {
		String memberId = getLoggedInMemberId();
		return cartService.getCartItems(memberId);
	}
	
	@PostMapping("")
	public String addItemToCart(@RequestBody CartItemRequestDTO cartItemRequest) {
		return cartService.addItemToCart(getLoggedInMemberId(), cartItemRequest.getItemId(),
				cartItemRequest.getQuantity());
	}
	
	@DeleteMapping("")
	public String deleteItems(@RequestParam(value = "item-ids", required = true) List<String> itemIds) {
		cartService.deleteItems(getLoggedInMemberId(), itemIds);
		return "Success!";
	}
	
	@DeleteMapping("/clear")
	public String clearCart() {
		cartService.clearCart(getLoggedInMemberId());
		return "Success";
	}
	
	@PostMapping("/purchase")
	public String purchaseItemsFromCart() {
		cartService.purchaseItemsFromCart(getLoggedInMemberId());
		return "The purchase is successful";
	}
	
	private String getLoggedInMemberId() {
		JwtUser jwtUser = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return jwtUser.getMemberId();
	}

}
