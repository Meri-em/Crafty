package com.crafty.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.crafty.dto.UploadItemDTO;
import com.crafty.entity.Author;
import com.crafty.entity.ItemImage;
import com.crafty.repository.AuthorRepository;
import com.crafty.repository.ItemImageRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.crafty.dto.ItemDTO;
import com.crafty.dto.SimpleItemDTO;
import com.crafty.entity.Item;
import com.crafty.repository.ItemRepository;
import com.crafty.util.MapperHelper;
import com.crafty.web.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ItemService {
	private static final Logger log = LoggerFactory.getLogger(ItemService.class);

	private static final String IMAGES_FOLDER = "src/main/resources/static/images/";
	
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final AuthorRepository authorRepository;
	private final MapperHelper mapperHelper;
	
	public ItemService(ItemRepository itemRepository,
		   ItemImageRepository itemImageRepository,
		   AuthorRepository authorRepository,
		   MapperHelper mapperHelper) {
		this.itemRepository = itemRepository;
		this.itemImageRepository = itemImageRepository;
		this.authorRepository = authorRepository;
		this.mapperHelper = mapperHelper;
	}

	public ItemDTO getItemById(String itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(""));
		return mapperHelper.toItemDTO(item);
	}
	
	public List<SimpleItemDTO> searchItems(String text, List<String> authorIds,
		   List<String> categories, BigDecimal minPrice, BigDecimal maxPrice, boolean archived) {
		String authorIdsString = CollectionUtils.isEmpty(authorIds) ? null : "";
		String categoriesString =  CollectionUtils.isEmpty(categories) ? null : "";
		
		List<Item> items = itemRepository.findItems(text, authorIdsString, authorIds, 
				categoriesString, categories, minPrice, maxPrice, archived);
		return items.stream()
			.map(item -> mapperHelper.toSimpleItemDTO(item))
			.collect(Collectors.toList());
	}

	public void addItem(String authorId, UploadItemDTO itemDTO, MultipartFile[] files) {
		Author author = authorRepository.findById(authorId)
			.orElseThrow(() -> new NotFoundException("No author found with id: " + authorId));
		String itemId = UUID.randomUUID().toString();
		Item item = new Item();
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		item.setCategory(itemDTO.getCategory());
		item.setAuthor(author);
		item.setId(itemId);
		item.setCreatedAt(Instant.now());

		List<ItemImage> itemImages = new ArrayList<>();
		int i = 0;
		for (MultipartFile file : files) {
			i++;
			String extension = file.getOriginalFilename().split("\\.")[1];
			ItemImage itemImage = new ItemImage();
			itemImage.setId(UUID.randomUUID().toString());
			itemImage.setItem(item);
			itemImage.setExtension(extension);
			String filePath = IMAGES_FOLDER + item.getId()+ "/"
				+ itemImage.getName();
			itemImage.setOrder(i);
			try {
				File imageFile = new File(filePath);
				imageFile.getParentFile().mkdirs();

				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filePath);

				Files.write(path, bytes);

			} catch (IOException e) {
				e.printStackTrace();
			}
			itemImages.add(itemImage);

		}
		item.setItemImages(itemImages);
		itemRepository.save(item);
	}

	public void updateItem(String authorId, String itemId, UploadItemDTO itemDTO) {
		Author author = authorRepository.findById(authorId)
			.orElseThrow(() -> new NotFoundException("No author found with id: " + authorId));

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new NotFoundException("No item found with id: " + itemId));
		if (item.getAuthor().getId().equals(authorId)) {
			throw new NotFoundException("No item found with id: " + itemId);
		}
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		item.setCategory(itemDTO.getCategory());
		item = itemRepository.save(item);
	}

	public void deleteItem(String authorId, String itemId) {
		Item item = itemRepository.findByAuthorIdAndId(authorId, itemId)
			.orElseThrow(() -> new NotFoundException("No such item found!"));
		item.setArchived(true);
		itemRepository.save(item);
	}

	public void deleteItemImage(String itemId, String authorId, String itemImageId) {
		ItemImage itemImage = itemImageRepository.findByItemIdAuthorIdAndId(itemId, authorId, itemImageId)
			.orElseThrow(() -> new NotFoundException("No such item image found!"));
		String filePath = IMAGES_FOLDER + itemId + "/"
			+ itemImage.getName();

		File imageFile = new File(filePath);
		if(imageFile.delete()){
			log.info(imageFile.getName() + " is deleted!");
			System.out.println();
		}else{
			log.error("Delete operation is failed.");
		}

		Item item = itemImage.getItem();
		item.getItemImages().remove(itemImage);
		itemRepository.save(item);
	}

	public void updateDefaultImage(String itemId, String authorId, String itemImageId) {
		ItemImage itemImage = itemImageRepository.findByItemIdAuthorIdAndId(itemId, authorId, itemImageId)
			.orElseThrow(() -> new NotFoundException("No such item image found!"));

		Item item = itemImage.getItem();
		Comparator<ItemImage> itemImageComparator = Comparator.comparing(ItemImage::getOrder);
		ItemImage firstImage = item.getItemImages().stream().sorted(itemImageComparator).findFirst().get();
		if (!itemImage.getId().equals(firstImage.getId())) {
			itemImage.setOrder(firstImage.getOrder() - 1);
			itemRepository.save(item);
		}
	}
}
