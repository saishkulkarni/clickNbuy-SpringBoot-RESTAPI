package com.jsp.clinkNBuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.clinkNBuy.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	boolean existsByMobile(Long mobile);

}
