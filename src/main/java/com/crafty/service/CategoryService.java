package com.crafty.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crafty.dto.CategoryItem;
import com.crafty.entity.Category;
import com.crafty.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<CategoryItem> getCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryItem> result = new ArrayList<>();
		List<Category> mainCategories = findByParent(null, categories);
		for (Category mainCategory : mainCategories) {
			CategoryItem categoryItem = new CategoryItem();
			categoryItem.setHref(mainCategory.getId());
			categoryItem.setText(mainCategory.getName());
			List<CategoryItem> items = new ArrayList<>();
			List<Category> subCategories = findByParent(mainCategory, categories);
			for (Category subCategory : subCategories) {
				CategoryItem subItem = new CategoryItem();
				subItem.setHref(subCategory.getId());
				subItem.setText(subCategory.getName());
				items.add(subItem);
			}
			categoryItem.setItems(items);
			result.add(categoryItem);
		}
		return result;
	}
	
	private List<Category> findByParent(Category parent, List<Category> categories) {
		Comparator<Category> categoryComparator = Comparator.comparing(Category::getOrder);
		return categories.stream()
				.filter(c -> {
					if (parent == null) {
						return c.getParent() == null;
						
					} else if (c.getParent() == null) {
						return false;
					} else {
						return c.getParent().equals(parent);
					}	
				})
				.sorted(categoryComparator)
				.collect(Collectors.toList());
	}
	

}
