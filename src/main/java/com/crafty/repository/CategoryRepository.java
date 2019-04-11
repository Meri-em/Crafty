package com.crafty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
