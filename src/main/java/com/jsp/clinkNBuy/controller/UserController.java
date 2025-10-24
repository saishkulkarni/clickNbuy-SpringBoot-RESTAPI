package com.jsp.clinkNBuy.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.AddProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

	UserService userService;

	@PostMapping("/cart/add")
	@Operation(summary = "Add to Cart")
	public ResponseDto addProductToCart(@RequestBody AddProductDto dto, Principal principal) {
		return userService.addProductToCart(dto,principal);
	}

}
