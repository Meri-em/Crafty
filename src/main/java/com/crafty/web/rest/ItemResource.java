package com.crafty.web.rest;

import java.math.BigDecimal;
import java.util.List;

import com.crafty.dto.UploadItemDTO;
import com.crafty.util.CurrentUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.crafty.dto.ItemDTO;
import com.crafty.dto.SimpleItemDTO;
import com.crafty.service.ItemService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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

	@PostMapping
	public String addItem(@Valid UploadItemDTO item,
				BindingResult bindingResult, final @RequestParam(value = "file") MultipartFile[] files){
		itemService.addItem(CurrentUser.getAuthorId(),
			item, files);
		return "Success!";
	}

	@PostMapping("/{itemId}")
	public String updateItem(@PathVariable String itemId, @RequestBody UploadItemDTO item) {
		itemService.updateItem(CurrentUser.getAuthorId(), itemId, item);
		return "Success!";
	}

	@DeleteMapping("/{itemId}")
	public String deleteItem(@PathVariable String itemId) {
		itemService.deleteItem(CurrentUser.getAuthorId(), itemId);
		return "Success!";
	}
}
