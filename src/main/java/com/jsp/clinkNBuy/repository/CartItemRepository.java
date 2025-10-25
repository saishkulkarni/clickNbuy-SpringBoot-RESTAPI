package com.jsp.clinkNBuy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.clinkNBuy.entity.CartItem;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	CartItem findByUserAndProduct(User user, Product product);

	List<CartItem> findByUser(User user);

}
