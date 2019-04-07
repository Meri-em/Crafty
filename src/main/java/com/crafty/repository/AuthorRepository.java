package com.crafty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {
	
	Optional<Author> findById(String id);

}
