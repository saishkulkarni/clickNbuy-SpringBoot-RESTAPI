package com.jsp.clinkNBuy.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.ProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.service.SellerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/seller")
@AllArgsConstructor
public class SellerController {

	SellerService sellerService;

	@PostMapping("/products")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto saveProduct(@Valid @RequestBody ProductDto productDto, Principal principal) {
		return sellerService.saveProduct(productDto, principal);
	}

	@GetMapping("/products")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto getProducts(Principal principal) {
		return sellerService.getProducts(principal);
	}

	@DeleteMapping("/products/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseDto deleteProduct(@PathVariable Long id,Principal principal) {
		return sellerService.deleteProduct(id,principal);
	}
}
