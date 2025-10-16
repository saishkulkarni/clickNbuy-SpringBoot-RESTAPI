package com.jsp.clinkNBuy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByNameAndBrandAndPrice(String name, String brand, Double price);

	List<Product> findByUser(User user, Pageable pageable);

}
