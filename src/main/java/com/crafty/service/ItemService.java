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

import com.crafty.dto.ItemArchivedDTO;
import com.crafty.dto.UploadItemDTO;
import com.crafty.entity.ItemImage;
import com.crafty.entity.Member;
import com.crafty.repository.ItemImageRepository;
import com.crafty.repository.MemberRepository;
import com.crafty.web.exception.BadRequestException;
import com.crafty.web.exception.UnauthorizedException;
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
	private static final int MAX_UPLOADED_PICTURES = 10;
	
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final MemberRepository memberRepository;
	private final MapperHelper mapperHelper;
	
	public ItemService(ItemRepository itemRepository,
		   ItemImageRepository itemImageRepository,
		   MemberRepository memberRepository,
		   MapperHelper mapperHelper) {
		this.itemRepository = itemRepository;
		this.itemImageRepository = itemImageRepository;
		this.memberRepository = memberRepository;
		this.mapperHelper = mapperHelper;
	}

	public ItemDTO getItemById(String itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(""));
		return mapperHelper.toItemDTO(item);
	}
	
	public List<SimpleItemDTO> searchItems(String text, List<String> memberIds,
		   List<String> categories, BigDecimal minPrice, BigDecimal maxPrice, boolean archived) {
		String memberIdsString = CollectionUtils.isEmpty(memberIds) ? null : "";
		String categoriesString =  CollectionUtils.isEmpty(categories) ? null : "";
		
		List<Item> items = itemRepository.findItems(text, memberIdsString, memberIds,
				categoriesString, categories, minPrice, maxPrice, archived);
		return items.stream()
			.map(item -> mapperHelper.toSimpleItemDTO(item))
			.collect(Collectors.toList());
	}

	public void addItem(String memberId, UploadItemDTO itemDTO, MultipartFile[] files) {
		if (files.length > MAX_UPLOADED_PICTURES) {
			throw new BadRequestException("Cannot add item with more than "
				+ MAX_UPLOADED_PICTURES + " pictures");
		}
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NotFoundException("No member found with id: " + memberId));
		Item item = mapperHelper.toItem(itemDTO, member);

		List<ItemImage> itemImages = new ArrayList<>();
		int imageFilesCount = 0;
		for (MultipartFile file : files) {
			if (!file.getOriginalFilename().isEmpty()) {
				ItemImage itemImage = new ItemImage();
				itemImage.setId(UUID.randomUUID().toString());
				itemImage.setItem(item);
				itemImage.setExtension(file.getOriginalFilename().split("\\.")[1]);
				String filePath = IMAGES_FOLDER + item.getId() + "/" + itemImage.getName();
				itemImage.setOrder(++imageFilesCount);
				try {
					new File(filePath).getParentFile().mkdirs();
					Files.write(Paths.get(filePath), file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				itemImages.add(itemImage);
			}
		}
		item.setItemImages(itemImages);
		itemRepository.save(item);
	}

	public void updateItem(String memberId, String itemId, UploadItemDTO itemDTO) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NotFoundException("No member found with id: " + memberId));

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new NotFoundException("No item found with id: " + itemId));
		if (!item.getMember().getId().equals(memberId)) {
			throw new NotFoundException("No item found with id: " + itemId);
		}
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		item.setCategory(itemDTO.getCategory());

		List<String> images = itemDTO.getImages();
		List<ItemImage> persistedItemImages = item.getItemImages();

		persistedItemImages.removeIf(pi -> !images.contains(pi.getId()));
		for (int i = 0; i < images.size(); i++) {
			for (ItemImage persistedItemImage : persistedItemImages) {
				if (persistedItemImage.getId().equals(images.get(i))) {
					persistedItemImage.setOrder(i + 1);
				}
			}
		}

		item = itemRepository.save(item);
	}

	public String updateArchived(String memberId, String itemId,
								 ItemArchivedDTO itemArchivedDTO){
		Item item = itemRepository.findByMemberIdAndId(memberId, itemId)
			.orElseThrow(() -> new NotFoundException("No such item found!"));
		item.setArchived(itemArchivedDTO.getArchived());
		item = itemRepository.save(item);
		if (item.isArchived()) {
			return "Артикулът е архивиривиран успешно";
		}
		return "Артикулът е възстановен успешно";
	}

	public void deleteItem(String memberId, String itemId) {
		Item item = itemRepository.findByMemberIdAndId(memberId, itemId)
			.orElseThrow(() -> new NotFoundException("No such item found!"));
		item.setArchived(true);
		itemRepository.save(item);
	}

	public void deleteItemImage(String itemId, String memberId, String itemImageId) {
		ItemImage itemImage = itemImageRepository.findByItemIdAndMemberIdAndId(itemId, memberId, itemImageId)
			.orElseThrow(() -> new UnauthorizedException("No such item for this author"));
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

	public void updateDefaultImage(String itemId, String memberId, String itemImageId) {
		ItemImage itemImage = itemImageRepository.findByItemIdAndMemberIdAndId(itemId, memberId, itemImageId)
			.orElseThrow(() -> new UnauthorizedException("No such item for this author"));

		Item item = itemImage.getItem();
		Comparator<ItemImage> itemImageComparator = Comparator.comparing(ItemImage::getOrder);
		ItemImage firstImage = item.getItemImages().stream().sorted(itemImageComparator).findFirst().get();
		if (!itemImage.getId().equals(firstImage.getId())) {
			itemImage.setOrder(firstImage.getOrder() - 1);
			itemRepository.save(item);
		}
	}
}
