package com.jsp.clinkNBuy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.entity.UserOrder;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

	Optional<UserOrder> findByOrderId(String razorpay_order_id);

	List<UserOrder> findByUser(User user);

}
