package com.jsp.clinkNBuy.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.ProductDao;
import com.jsp.clinkNBuy.dao.UserDao;
import com.jsp.clinkNBuy.dto.AddProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.exception.OutOfStockException;
import com.jsp.clinkNBuy.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	ProductDao productDao;
	UserDao userDao;

	@Override
	public ResponseDto getAllApprovedProducts(int page, int size, String sortBy, boolean desc) {
		Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy);
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		List<Product> products = productDao.findApprovedProducts(pageable);
		return new ResponseDto("Products Found", products);
	}

	@Override
	public ResponseDto addProductToCart(AddProductDto dto, Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		Product product = productDao.findProductByIdAndApprovedTrue(dto.getId());
		if (dto.getQuantity()>product.getStock())
			throw new OutOfStockException(product.getName() + " is Not in Stock");
		else
			return new ResponseDto("Added to Cart Success", product);
	}

}
