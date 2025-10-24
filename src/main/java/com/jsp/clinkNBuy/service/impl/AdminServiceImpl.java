package com.jsp.clinkNBuy.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.CategoryDao;
import com.jsp.clinkNBuy.dao.ProductDao;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Category;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.exception.DataExistsException;
import com.jsp.clinkNBuy.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	CategoryDao categoryDao;
	ProductDao productDao;

	@Override
	public ResponseDto addCategory(Category category) {
		if (categoryDao.isCategoryUnique(category.getName())) {
			categoryDao.save(category);
			return new ResponseDto("Category Added Success", category);
		} else
			throw new DataExistsException(category.getName() + " Already Present");
	}

	@Override
	public ResponseDto viewCategories() {
		return new ResponseDto("Found Success", categoryDao.findAllCategory());
	}

	@Override
	public ResponseDto deleteCategory(Long id) {
		categoryDao.deleteCategory(id);
		return new ResponseDto("Deleted Success", null);
	}

	@Override
	public ResponseDto updateCategory(Long id, Category req) {
		Category category = categoryDao.findCategoryById(id);
		category.setName(req.getName());
		categoryDao.save(category);

		return new ResponseDto("Category Updated Success", category);
	}

	@Override
	public ResponseDto getProducts(int page, int size, String sortBy, boolean desc) {
		Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy);
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		List<Product> products = productDao.getAllProducts(pageable);
		return new ResponseDto("Products Found", products);
	}

	@Override
	public ResponseDto changeProductStatus(Long id) {
		Product product = productDao.findProductById(id);
		product.setApproved(product.isApproved()?false:true);
		productDao.saveProduct(product);
		return new ResponseDto("Product Status Changed Success", product);
	}

}
