package com.crafty.repository;

import com.crafty.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {

	Optional<Review> findByMemberIdAndItemId(String memberId, String itemId);

	Integer deleteByMemberIdAndItemId(String memberId, String itemId);

	List<Review> findByItemIdOrderByLastUpdatedDesc(String itemId);

}
