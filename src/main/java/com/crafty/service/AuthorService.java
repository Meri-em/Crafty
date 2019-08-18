package com.crafty.service;

import com.crafty.dto.UploadItemDTO;
import com.crafty.entity.Author;
import com.crafty.entity.Item;
import com.crafty.entity.ItemImage;
import com.crafty.entity.User;
import com.crafty.enumeration.UserRole;
import com.crafty.repository.AuthorRepository;
import com.crafty.repository.ItemRepository;
import com.crafty.repository.UserRepository;
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

	private final AuthorRepository authorRepository;
	private final UserRepository userRepository;

	public AuthorService(AuthorRepository authorRepository,
						 UserRepository userRepository) {
		this.authorRepository = authorRepository;
		this.userRepository = userRepository;
	}

	public String createAuthor(String memberId) {
		User user = userRepository.findByMemberId(memberId).
			orElseThrow(() -> new NotFoundException("Member with id: " + memberId + " not found"));
		Author author = new Author();
		authorRepository.save(author);
		user.setAuthorId(author.getId());
		user.setRole(UserRole.ROLE_AUTHOR);
		userRepository.save(user);
		return author.getId();
	}


}
