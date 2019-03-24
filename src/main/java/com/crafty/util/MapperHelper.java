package com.crafty.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.crafty.dto.AuthorDTO;
import com.crafty.dto.ItemDTO;
import com.crafty.dto.ItemImageDTO;
import com.crafty.dto.SimpleItemDTO;
import com.crafty.entity.Author;
import com.crafty.entity.Item;
import com.crafty.entity.ItemImage;

@Component
public class MapperHelper {
	
	private static final String IMAGES_DIRECTORY = "/images";
	
	public AuthorDTO toAuthorDTO(Author author) {
		AuthorDTO authorDTO = new AuthorDTO();
		authorDTO.setId(author.getId());
		authorDTO.setName(author.getName());
		return authorDTO;
	}
	
	public ItemImageDTO toItemImageDTO(ItemImage itemImage) {
		ItemImageDTO itemImageDTO = new ItemImageDTO();
		itemImageDTO.setId(itemImage.getId());
		itemImageDTO.setPath(getImagePath(itemImage));
		return itemImageDTO;
	}

	public SimpleItemDTO toSimpleItemDTO(Item item) {
		AuthorDTO author = toAuthorDTO(item.getAuthor());
		String primaryImagePath = null;
		
		if (!item.getItemImages().isEmpty()) {
			Comparator<ItemImage> itemImageComparator = Comparator.comparing(ItemImage::getOrder);
			ItemImage itemImage = item.getItemImages().stream().sorted(itemImageComparator).findFirst().get();
			primaryImagePath = getImagePath(itemImage);
		}
		SimpleItemDTO simpleItem = new SimpleItemDTO(item.getId(), item.getName(),item.getPrice(),
				author, primaryImagePath);
		return simpleItem;
	}
	
	public ItemDTO toItemDTO(Item item) {
		ItemDTO itemDTO = new ItemDTO(toSimpleItemDTO(item));
		itemDTO.setDescription(item.getDescription());
		itemDTO.setCreatedAt(LocalDateTime.ofInstant(item.getCreatedAt(), ZoneOffset.UTC));
		
		Comparator<ItemImage> itemImageComparator = Comparator.comparing(ItemImage::getOrder);
		List<ItemImageDTO> itemImages = item.getItemImages().stream().sorted(itemImageComparator)
				.map(it -> toItemImageDTO(it)).collect(Collectors.toList());
		itemDTO.setItemImages(itemImages);
		return itemDTO;
	}
	
	private String getImagePath(ItemImage itemImage) {
		return String.format("%s/%s/%s", IMAGES_DIRECTORY, itemImage.getItem().getId(), itemImage.getName());
	}
}
