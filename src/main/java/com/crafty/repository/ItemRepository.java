package com.crafty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crafty.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
	
	Optional<Item> findById(String id);
	
	List<Item> findByCategoryOrderByCreatedAtDesc(String category);
	
	@Query(value = "SELECT it FROM Item it"
			+ "  JOIN it.author author"
	        + "  WHERE (:text IS NULL OR it.name LIKE CONCAT('%', :text, '%') OR it.description LIKE CONCAT('%', :text, '%'))"
	        + "  AND (:categoriesString IS NULL OR it.category IN (:categories))"
			+ "  AND (:authorIdsString IS NULL OR author.id IN (:authorIds))"
	        + "  AND (:minPrice IS NULL OR it.price >= :minPrice)"
	        + "  AND (:maxPrice IS NULL OR it.price <= :maxPrice)")
	List<Item> findItems(@Param("text") String text, 
			@Param("authorIdsString") String authorIdsString,
			@Param("authorIds") List<String> authorIds,
			@Param("categoriesString") String categoriesString,
			@Param("categories") List<String> categories,
			@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
	
//	@Query(value = "SELECT * FROM item\n"
//	        + "INNER JOIN member ON member.id = member_coverage.member_id\n"
//	        + "WHERE member.employer_id IN (:employerIds)\n"
//	        + "  AND is_core_coverage = FALSE\n"
//	        + "  AND addin_status != 'CANCELED'\n"
//	        + "  AND (:startDate IS NULL OR purchased_date >= :startDate)\n"
//	        + "  AND (:endDate IS NULL OR purchased_date < :endDate) \n", nativeQuery = true)

}
