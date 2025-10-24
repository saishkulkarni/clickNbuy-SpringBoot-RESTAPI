package com.jsp.clinkNBuy.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ProductDao {

	ProductRepository productRepository;

	public List<Product> getAllProducts(Pageable pageable) {
		List<Product> products = productRepository.findAll(pageable).getContent();
		if (products.isEmpty())
			throw new DataNotFoundException("No Products Present");
		return products;
	}
	
	public void saveProduct(Product prodcut) {
		productRepository.save(prodcut);
	}
	
	public boolean isProductUnique(String name, String brand, Double price) {
		return !productRepository.existsByNameAndBrandAndPrice(name, brand, price);
	}

	public List<Product> fetchProducts(User user, Pageable pageable) {
		List<Product> products = productRepository.findByUser(user,pageable);
		if (products.isEmpty())
			throw new DataNotFoundException("No Products Present");
		else
			return products;
	}

	public Product findProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Product Not Found with Id: " + id));
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public List<Product> saveAllProducts(List<Product> products) {
		return productRepository.saveAll(products);
	}

	public List<Product> findApprovedProducts(Pageable pageable) {
		List<Product> products = productRepository.findByApprovedTrue(pageable);
		if (products.isEmpty())
			throw new DataNotFoundException("No Products Present");
		return products;
	}

	public Product findProductByIdAndApprovedTrue(Long id) {
		return productRepository.findByIdAndApprovedTrue(id)
				.orElseThrow(() -> new DataNotFoundException("Product Not Found with Id: " + id));
	}

}
