package com.jsp.clinkNBuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.clinkNBuy.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);

}
