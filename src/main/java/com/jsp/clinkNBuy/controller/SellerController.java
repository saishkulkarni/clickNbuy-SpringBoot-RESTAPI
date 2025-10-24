package com.jsp.clinkNBuy.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.ProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.service.SellerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/seller")
@AllArgsConstructor
public class SellerController {

	SellerService sellerService;

	@PostMapping("/products")
	@Operation(summary = "Add Seller Products", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto saveProduct(@Valid @RequestBody ProductDto productDto, Principal principal) {
		return sellerService.saveProduct(productDto, principal);
	}

	@GetMapping("/products")
	@Operation(summary = "Fetch Seller Products", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto getProducts(Principal principal, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "false") boolean desc) {
		return sellerService.getProducts(principal, page, size, sort, desc);
	}

	@DeleteMapping("/products/{id}")
	@Operation(summary = "Delete Seller Product", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseDto deleteProduct(@PathVariable Long id, Principal principal) {
		return sellerService.deleteProduct(id, principal);
	}

	@PostMapping("/products/extra")
	@Operation(hidden = true)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto addProducts(Principal principal) {
		return sellerService.addProducts(principal);
	}

	@PutMapping("/products/{id}")
	@Operation(summary = "Update Seller Product", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto deleteProduct(@PathVariable Long id, @RequestBody Product product, Principal principal) {
		return sellerService.updateProduct(id, product, principal);
	}
}
