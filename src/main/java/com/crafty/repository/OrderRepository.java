package com.crafty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, String>  {

	@Query(value = "SELECT o FROM Order o"
		+ "  WHERE o.memberId = :memberId"
		+ "  ORDER BY o.createdAt DESC")
	List<Order> findByMemberId(@Param("memberId") String memberId);

}
