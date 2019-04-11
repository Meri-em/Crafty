package com.crafty.web.rest;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crafty.dto.CategoryItem;
import com.crafty.service.CategoryService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/categories")
public class CategoryResource {
	
	private CategoryService categoryService;
	
	public CategoryResource(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("")
	public List<CategoryItem> getCategories() {
		return categoryService.getCategories();
	}

}
