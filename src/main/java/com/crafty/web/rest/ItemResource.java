package com.crafty.web.rest;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.ItemDTO;
import com.crafty.dto.SimpleItemDTO;
import com.crafty.service.ItemService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/items")
public class ItemResource {
	private final ItemService itemService;public ItemResource(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/{itemId}")
	public ItemDTO getItemById(@PathVariable String itemId) {
		return itemService.getItemById(itemId);
	}

	@GetMapping("/search")
	public List<SimpleItemDTO> getItemsByCategory(
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "author-ids", required = false) List<String> authorIds,
			@RequestParam(value = "categories", required = false) List<String> categories,
			@RequestParam(value = "min-price", required = false) BigDecimal minPrice,
			@RequestParam(value = "max-price", required = false) BigDecimal maxPrice) {
		return itemService.searchItems(text, authorIds, categories, minPrice, maxPrice);

	}
	
	@PostMapping("")
	public String addItem() {
		return "Add a new item";
	}
}
