package com.jsp.clinkNBuy.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.CartItemDao;
import com.jsp.clinkNBuy.dao.ProductDao;
import com.jsp.clinkNBuy.dao.UserDao;
import com.jsp.clinkNBuy.dto.CartItemDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.CartItem;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.exception.OutOfStockException;
import com.jsp.clinkNBuy.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	ProductDao productDao;
	UserDao userDao;
	CartItemDao itemDao;

	@Override
	public ResponseDto getAllApprovedProducts(int page, int size, String sortBy, boolean desc) {
		Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy);
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		List<Product> products = productDao.findApprovedProducts(pageable);
		return new ResponseDto("Products Found", products);
	}

	@Override
	public ResponseDto addProductToCart(CartItemDto dto, Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		Product product = productDao.findProductByIdAndApprovedTrue(dto.getId());
		if (dto.getQuantity() > product.getStock())
			throw new OutOfStockException(product.getName() + " is Not in Stock");
		else {
			CartItem exItem = itemDao.getIfExists(user, product);
			if (exItem != null) {
				exItem.setQuantity(exItem.getQuantity() + dto.getQuantity());
				itemDao.save(exItem);
				return new ResponseDto("Item Increased Success", exItem);
			} else {
				CartItem item = new CartItem(null, product, dto.getQuantity(), user);
				itemDao.save(item);
				return new ResponseDto("Item Added Success", item);
			}
		}
	}

	@Override
	public ResponseDto removeFromCart(CartItemDto dto, Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		Product product = productDao.findProductByIdAndApprovedTrue(dto.getId());
		CartItem exItem = itemDao.getIfExists(user, product);
		if (exItem == null)
			throw new DataNotFoundException("Item is Not Present in Cart");
		else {
			if (exItem.getQuantity() < dto.getQuantity())
				throw new DataNotFoundException("In Cart only " + exItem.getQuantity() + " item is there");
			else {
				if (exItem.getQuantity() == dto.getQuantity()) {
					itemDao.deleteById(exItem.getId());
					return new ResponseDto("Item Removed Success", exItem);
				} else {
					exItem.setQuantity(exItem.getQuantity() - dto.getQuantity());
					itemDao.save(exItem);
					return new ResponseDto("Item Removed Success", exItem);
				}
			}
		}
	}

	@Override
	public ResponseDto viewCart(Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		List<CartItem> items = itemDao.getCartItems(user);
		return new ResponseDto("Data Found", items);
	}

}
