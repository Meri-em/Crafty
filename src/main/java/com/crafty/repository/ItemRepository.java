package com.crafty.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crafty.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
	
	Optional<Item> findById(String id);

	@Query(value = "SELECT it FROM Item it"
		+ " JOIN it.member m"
		+ " WHERE (m.id = :memberId"
		+ " AND it.id = :id)")
	Optional<Item> findByMemberIdAndId(@Param("memberId") String memberId,
							   @Param("id") String id);
	
	@Query(value = "SELECT it FROM Item it"
			+ " JOIN it.member m"
	        + " WHERE (:text IS NULL OR"
	        + " LOWER(it.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(it.description) LIKE LOWER(CONCAT('%', :text, '%')))"
	        + " AND (:categoriesString IS NULL OR it.category IN (:categories))"
			+ " AND (:memberIdsString IS NULL OR m.id IN (:memberIds))"
	        + " AND (:minPrice IS NULL OR it.price >= :minPrice)"
	        + " AND (:maxPrice IS NULL OR it.price <= :maxPrice)"
	        + " AND it.archived = :archived"
	        + " ORDER BY it.createdAt DESC")
	List<Item> findItems(@Param("text") String text,
						 @Param("memberIdsString") String memberIdsString,
						 @Param("memberIds") List<String> memberIds,
						 @Param("categoriesString") String categoriesString,
						 @Param("categories") List<String> categories,
						 @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice,
						 @Param("archived") boolean archived);

}
