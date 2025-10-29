package com.jsp.clinkNBuy.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.AddressDto;
import com.jsp.clinkNBuy.dto.CartItemDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.service.UserService;
import com.razorpay.RazorpayException;

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

	@PostMapping("/order")
	@Operation(summary = "Place Order")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto createOrder(@RequestBody AddressDto dto, Principal principal) throws RazorpayException {
		return userService.createOrder(dto, principal);
	}

	@PostMapping("/payment")
	@Operation(summary = "Confirm Payment")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto confirmOrder(@RequestParam String razorpay_payment_id, @RequestParam String razorpay_order_id) {
		return userService.confirmOrder(razorpay_order_id, razorpay_payment_id);
	}

	@GetMapping("/orders")
	@Operation(summary = "Fetch All Orders")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto fetchOrders(Principal principal) {
		return userService.fetchAllOrders(principal);
	}

}
