package com.crafty.service;

import com.crafty.dto.UploadItemDTO;
import com.crafty.entity.Author;
import com.crafty.entity.Item;
import com.crafty.entity.ItemImage;
import com.crafty.repository.AuthorRepository;
import com.crafty.repository.ItemRepository;
import com.crafty.web.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthorService {

	private final ItemRepository itemRepository;
	private final AuthorRepository authorRepository;

	public AuthorService(ItemRepository itemRepository,
			 AuthorRepository authorRepository) {
		this.itemRepository = itemRepository;
		this.authorRepository = authorRepository;
	}

	public String addNewItem(String authorId, UploadItemDTO itemDTO, MultipartFile[] files) {
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
			ItemImage itemImage = new ItemImage();
			itemImage.setItem(item);
			String originalName = file.getOriginalFilename();
			String extentsion = originalName.split("\\.")[1];
			String fileName = i + "." + extentsion;
			String filePath = "src/main/resources/static/images/" + item.getId()+ "/" + fileName;
			itemImage.setName(fileName);
			itemImage.setOrder(i);
			try {
				File file1 = new File(filePath);
				file1.getParentFile().mkdirs();

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
		item = itemRepository.save(item);
		return "Success";
	}
}
