package com.jsp.clinkNBuy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jsp.clinkNBuy.entity.Category;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CategoryDao {
	CategoryRepository categoryRepository;

	public Category getCategory(String category) {
		return categoryRepository.findByName(category);
	}

	public boolean isCategoryPresent(String category) {
		return categoryRepository.existsByName(category);
	}

	public boolean isCategoryUnique(String name) {
		return !categoryRepository.existsByName(name);
	}

	public void save(Category category) {
		categoryRepository.save(category);
	}

	public List<Category> findAllCategory() {
		List<Category> list = categoryRepository.findAll();
		if (list.isEmpty())
			throw new DataNotFoundException("No Categories Present");
		else
			return list;
	}

	public void deleteCategory(Long id) {
		findCategoryById(id);
		categoryRepository.deleteById(id);
	}

	public Category findCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Category Not Found"));
	}

}
