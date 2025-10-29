package com.jsp.clinkNBuy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.entity.UserOrder;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.repository.UserOrderRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class OrderDao {
	UserOrderRepository orderRepository;

	public void save(UserOrder userOrder) {
		orderRepository.save(userOrder);
	}

	public UserOrder findByOrderId(String razorpay_order_id) {
		return orderRepository.findByOrderId(razorpay_order_id)
				.orElseThrow(() -> new DataNotFoundException("No Order Found"));
	}

	public List<UserOrder> fetchAllOrders(User user) {
		List<UserOrder> orders = orderRepository.findByUser(user);
		if (orders.isEmpty())
			throw new DataNotFoundException("No Orders Found");
		return orders;
	}
}
