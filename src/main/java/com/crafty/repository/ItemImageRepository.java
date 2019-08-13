package com.crafty.repository;

import com.crafty.entity.Item;
import com.crafty.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, String> {

	@Query(value = "SELECT im FROM ItemImage im"
		+ " JOIN im.item item"
		+ " JOIN item.author author"
		+ " WHERE item.id = :itemId AND author.id = :authorId"
		+ " AND im.id = :id")
	Optional<ItemImage> findByItemIdAuthorIdAndId(@Param("itemId") String itemId,
		  @Param("authorId") String authorId, @Param("id") String id);
}
