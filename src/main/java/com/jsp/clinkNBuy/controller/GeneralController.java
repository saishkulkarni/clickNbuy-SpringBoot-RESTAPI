package com.jsp.clinkNBuy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class GeneralController {

	UserService userService;

	@GetMapping("/products")
	@Operation(summary = "Fetch Approved Products")
	public ResponseDto fetAllProducts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "false") boolean desc) {
		return userService.getAllApprovedProducts(page, size, sort, desc);
	}

}
