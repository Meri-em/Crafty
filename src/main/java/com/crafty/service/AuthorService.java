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


}
