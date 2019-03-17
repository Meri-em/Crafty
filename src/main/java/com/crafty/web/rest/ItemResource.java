package com.crafty.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.ItemDTO;
import com.crafty.dto.SimpleItemDTO;
import com.crafty.service.ItemService;

@RestController
@RequestMapping("api/v1/items")
public class ItemResource {
	
	private final ItemService itemService;
	
	public ItemResource(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping("/{itemId}")
	public ItemDTO getItemById(@PathVariable String itemId) {
		return itemService.getItemById(itemId);
	}
	
	@GetMapping("")
	public List<SimpleItemDTO> getItemsByCategory(@RequestParam("category") String category) {
		return itemService.getItemsByCategory(category);
	}
}
