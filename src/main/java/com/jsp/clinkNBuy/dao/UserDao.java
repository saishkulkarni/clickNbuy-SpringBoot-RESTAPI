package com.jsp.clinkNBuy.dao;

import org.springframework.stereotype.Repository;

import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.repository.UserRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserDao {
	UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public boolean isEmailUnique(String email) {
		return !userRepository.existsByEmail(email);
	}

	public boolean isMobileUnique(Long mobile) {
		return !userRepository.existsByMobile(mobile);
	}
}
