package com.crafty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
	
	Optional<Item> findById(String id);
	
	List<Item> findByCategoryOrderByCreatedAtDesc(String category);

}
