package com.crafty.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.crafty.dto.ItemDTO;
import com.crafty.dto.SimpleItemDTO;
import com.crafty.entity.Item;
import com.crafty.repository.ItemRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.NotFoundException;

@Service
public class ItemService {
	
	private final ItemRepository itemRepository;
	private final MapperHelper mapperHelper;
	
	public ItemService(ItemRepository itemRepository, MapperHelper mapperHelper) {
		this.itemRepository = itemRepository;
		this.mapperHelper = mapperHelper;
	}

	public ItemDTO getItemById(String itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(""));
		return mapperHelper.toItemDTO(item);
	}
	
	public List<SimpleItemDTO> searchItems(String text, List<String> authorIds,
			List<String> categories, Double minPrice, Double maxPrice) {
		String authorIdsString = CollectionUtils.isEmpty(authorIds) ? null : "";
		String categoriesString =  CollectionUtils.isEmpty(categories) ? null : "";
		
		List<Item> items = itemRepository.findItems(text, authorIdsString, authorIds, 
				categoriesString, categories, minPrice, maxPrice);
		return items.stream()
			.map(item -> mapperHelper.toSimpleItemDTO(item))
			.collect(Collectors.toList());
	}
}
