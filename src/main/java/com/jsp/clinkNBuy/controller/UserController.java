package com.jsp.clinkNBuy.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.CartItemDto;
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
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto addProductToCart(@RequestBody CartItemDto dto, Principal principal) {
		return userService.addProductToCart(dto, principal);
	}

	@DeleteMapping("/cart/remove")
	@Operation(summary = "Remove from Cart")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto removeProduct(@RequestBody CartItemDto dto, Principal principal) {
		return userService.removeFromCart(dto, principal);
	}

	@GetMapping("/cart")
	@Operation(summary = "View Cart")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto viewCart(Principal principal) {
		return userService.viewCart(principal);
	}

}
