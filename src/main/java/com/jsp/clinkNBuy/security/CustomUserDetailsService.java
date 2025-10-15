package com.jsp.clinkNBuy.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.exception.AccountNotVerifiedException;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("Invalid Email"));
		if (!user.isStatus()) {
			throw new AccountNotVerifiedException("Account not verified. Please verify OTP.");
		}
		return new CustomUser(user);
	}

}
