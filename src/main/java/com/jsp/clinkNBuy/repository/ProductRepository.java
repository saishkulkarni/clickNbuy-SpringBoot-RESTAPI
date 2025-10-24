package com.jsp.clinkNBuy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByNameAndBrandAndPrice(String name, String brand, Double price);

	List<Product> findByUser(User user, Pageable pageable);

	List<Product> findByApprovedTrue(Pageable pageable);

	Optional<Product> findByIdAndApprovedTrue(Long id);

}
