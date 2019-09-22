package com.crafty.repository;

import com.crafty.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, String> {

	@Query(value = "SELECT im FROM ItemImage im"
		+ " JOIN im.item item"
		+ " JOIN item.member m"
		+ " WHERE item.id = :itemId AND m.id = :memberId"
		+ " AND im.id = :id")
	Optional<ItemImage> findByItemIdAndMemberIdAndId(@Param("itemId") String itemId,
		  @Param("memberId") String memberId, @Param("id") String id);
}
