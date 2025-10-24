package com.jsp.clinkNBuy.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jsp.clinkNBuy.dao.CategoryDao;
import com.jsp.clinkNBuy.dao.ProductDao;
import com.jsp.clinkNBuy.dao.UserDao;
import com.jsp.clinkNBuy.dto.ProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.exception.DataExistsException;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.service.SellerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

	CategoryDao categoryDao;
	ProductDao productDao;
	UserDao userDao;
	RestTemplate restTemplate;

	@Override
	public ResponseDto saveProduct(ProductDto dto, Principal principal) {
		if (categoryDao.isCategoryPresent(dto.getCategory())) {
			if (productDao.isProductUnique(dto.getName(), dto.getBrand(), dto.getPrice())) {
				Product product = new Product(null, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock(),
						dto.getImageLink(), categoryDao.getCategory(dto.getCategory()), dto.getBrand(), false,
						userDao.findByEmail(principal.getName()));
				productDao.saveProduct(product);
				return new ResponseDto("Product Added Success", product);
			} else {
				throw new DataExistsException("Product Already Exists");
			}
		} else {
			throw new DataNotFoundException("No Category with name: " + dto.getCategory());
		}
	}

	@Override
	public ResponseDto getProducts(Principal principal, int page, int size, String sortBy, boolean desc) {
		User user = userDao.findByEmail(principal.getName());
		Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy);
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		List<Product> products = productDao.fetchProducts(user, pageable);
		return new ResponseDto("Products Found", products);
	}

	@Override
	public ResponseDto deleteProduct(Long id, Principal principal) {
		Product product = productDao.findProductById(id);
		if (product.getUser().getEmail().equals(principal.getName()))
			productDao.deleteProduct(id);
		else
			throw new AuthorizationDeniedException("You can not deleted this product");
		return new ResponseDto("Product Deleted Success", product);
	}

	@Override
	public ResponseDto addProducts(Principal principal) {
		Map<String, List<Map<String, Object>>> map = restTemplate.getForObject("https://dummyjson.com/products",
				Map.class);
		List<Product> products = new ArrayList<Product>();
		for (Map<String, Object> productDto : map.get("products")) {
			String category = (String) productDto.get("category");
			String name = (String) productDto.get("title");
			String description = (String) productDto.get("description");
			String brand = (String) productDto.get("brand");
			String imageLink = ((List<String>) productDto.get("images")).get(0);
			Double price = ((Double) productDto.get("price")) * 87.87;
			Integer stock = (Integer) productDto.get("stock");

			if (categoryDao.isCategoryPresent(category)) {
				if (productDao.isProductUnique(name, brand, price)) {
					Product product = new Product(null, name, description, price, stock, imageLink,
							categoryDao.getCategory(category), brand, false, userDao.findByEmail(principal.getName()));
					products.add(product);
				} else {
					throw new DataExistsException("Product Already Exists " + name);
				}
			} else {
				throw new DataNotFoundException("No Category with name: " + category);
			}
		}
		return new ResponseDto("Product Added Success", productDao.saveAllProducts(products));
	}

	@Override
	public ResponseDto updateProduct(Long id, Product product, Principal principal) {
		Product existingProduct = productDao.findProductById(id);
		if (existingProduct.getUser().getEmail().equals(principal.getName())) {
			product.setId(id);
			product.setUser(existingProduct.getUser());
			productDao.saveProduct(product);
		} else
			throw new AuthorizationDeniedException("You can not deleted this product");
		return new ResponseDto("Product Updated Success", product);
	}

}
