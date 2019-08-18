package com.crafty.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crafty.entity.Item;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepository extends JpaRepository<Item, String> {
	
	Optional<Item> findById(String id);
	
	List<Item> findByCategoryOrderByCreatedAtDesc(String category);

	@Query(value = "SELECT it FROM Item it"
		+ " JOIN it.author author"
		+ " WHERE (author.id = :authorId"
		+ " AND it.id = :id)")
	Optional<Item> findByAuthorIdAndId(@Param("authorId") String authorId,
							   @Param("id") String id);
	
	@Query(value = "SELECT it FROM Item it"
			+ " JOIN it.author author"
	        + " WHERE (:text IS NULL OR"
	        + " LOWER(it.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(it.description) LIKE LOWER(CONCAT('%', :text, '%')))"
	        + " AND (:categoriesString IS NULL OR it.category IN (:categories))"
			+ " AND (:authorIdsString IS NULL OR author.id IN (:authorIds))"
	        + " AND (:minPrice IS NULL OR it.price >= :minPrice)"
	        + " AND (:maxPrice IS NULL OR it.price <= :maxPrice)"
	        + " AND it.archived = :archived"
	        + " ORDER BY it.createdAt DESC")
	List<Item> findItems(@Param("text") String text,
						 @Param("authorIdsString") String authorIdsString,
						 @Param("authorIds") List<String> authorIds,
						 @Param("categoriesString") String categoriesString,
						 @Param("categories") List<String> categories,
						 @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice,
						 @Param("archived") boolean archived);

}
