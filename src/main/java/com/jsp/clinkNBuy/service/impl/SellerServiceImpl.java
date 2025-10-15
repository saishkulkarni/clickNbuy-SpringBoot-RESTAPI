package com.jsp.clinkNBuy.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.SellerDao;
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

	SellerDao sellerDao;
	UserDao userDao;

	@Override
	public ResponseDto saveProduct(ProductDto dto, Principal principal) {
		if (sellerDao.isCategoryPresent(dto.getCategory())) {
			if (sellerDao.isProductUnique(dto.getName(), dto.getBrand(), dto.getPrice())) {
				Product product = new Product(null, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock(),
						dto.getImageLink(), sellerDao.getCategory(dto.getCategory()), dto.getBrand(), false,
						userDao.findByEmail(principal.getName()));
				sellerDao.saveProduct(product);
				return new ResponseDto("Product Added Success", product);
			} else {
				throw new DataExistsException("Product Already Exists");
			}
		} else {
			throw new DataNotFoundException("No Category with name: " + dto.getCategory());
		}
	}

	@Override
	public ResponseDto getProducts(Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		List<Product> products = sellerDao.fetchProducts(user);
		return new ResponseDto("Products Found", products);
	}

	@Override
	public ResponseDto deleteProduct(Long id, Principal principal) {
		Product product = sellerDao.findProductById(id);
		if (product.getUser().getEmail().equals(principal.getName()))
			sellerDao.deleteProduct(id);
		else
			throw new AuthorizationDeniedException("You can not deleted this product");
		return new ResponseDto("Product Deleted Success", product);
	}

}
