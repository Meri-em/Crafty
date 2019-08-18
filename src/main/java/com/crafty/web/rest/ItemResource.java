package com.crafty.web.rest;

import java.math.BigDecimal;
import java.util.List;

import com.crafty.dto.DefaultImageDTO;
import com.crafty.dto.UploadItemDTO;
import com.crafty.util.CurrentUser;
import com.crafty.web.exception.BadRequestException;
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
	private final ItemService itemService;

	public ItemResource(ItemService itemService) {
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
			@RequestParam(value = "max-price", required = false) BigDecimal maxPrice,
			@RequestParam(value = "archived", required = false) boolean archived) {
		return itemService.searchItems(text, authorIds, categories, minPrice, maxPrice, archived);
	}

	@PostMapping
	public String addItem(@Valid UploadItemDTO item,
				BindingResult bindingResult, final @RequestParam(value = "file") MultipartFile[] files){
		if (item.getName() == null) {
			throw new BadRequestException("The item name must be added!");
		}
		String authorId;
		itemService.addItem(CurrentUser.getMemberId(), item, files);
		return "Success!";
	}

	@PostMapping("/{itemId}")
	public String updateItem(@PathVariable String itemId, @RequestBody UploadItemDTO item) {
		itemService.updateItem(CurrentUser.getMemberId(), itemId, item);
		return "Success!";
	}

	@DeleteMapping("/{itemId}")
	public String deleteItem(@PathVariable String itemId) {
		itemService.deleteItem(CurrentUser.getMemberId(), itemId);
		return "Success!";
	}

	@DeleteMapping("/{itemId}/images/{itemImageId}")
	public String deleteItemImage(@PathVariable String itemId, @PathVariable String itemImageId) {
		itemService.deleteItemImage(itemId, CurrentUser.getMemberId(), itemImageId);
		return "Success!";
	}

	@PostMapping("/{itemId}/images/default")
	public String updateDefaultImage(@PathVariable String itemId,
									 @RequestBody DefaultImageDTO defaultImageDTO) {
		itemService.updateDefaultImage(itemId, CurrentUser.getMemberId(), defaultImageDTO.getId());
		return "Success!";
	}


}
