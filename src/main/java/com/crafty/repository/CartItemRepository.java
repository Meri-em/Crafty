package com.crafty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
	
	List<CartItem> findByMemberId(String memberId);
	
	Optional<CartItem> findByMemberIdAndItemId(String memberId, String itemId);
	
	void deleteByMemberIdAndItemIdIn(String memberId, List<String> itemIds);
	
	void deleteByMemberId(String memberId);

}
