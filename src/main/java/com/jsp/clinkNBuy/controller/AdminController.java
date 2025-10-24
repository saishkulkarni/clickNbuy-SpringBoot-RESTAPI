package com.jsp.clinkNBuy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Category;
import com.jsp.clinkNBuy.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

	AdminService adminService;

	@PostMapping("/category")
	@Operation(summary = "Add Category", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto addCategory(@RequestBody Category category) {
		return adminService.addCategory(category);
	}

	@GetMapping("/category")
	@Operation(summary = "Fetch All Categories", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto viewCategories() {
		return adminService.viewCategories();
	}

	@DeleteMapping("/category/{id}")
	@Operation(summary = "Delete Category", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseDto deleteCategory(@PathVariable Long id) {
		return adminService.deleteCategory(id);
	}

	@PutMapping("/category/{id}")
	@Operation(summary = "Update Category", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto updateCategory(@PathVariable Long id, @RequestBody Category category) {
		return adminService.updateCategory(id, category);
	}

	@GetMapping("/products")
	@Operation(summary = "Fetch All Products", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto getProducts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "false") boolean desc) {
		return adminService.getProducts(page, size, sort, desc);
	}

	@PatchMapping("/products/{id}")
	@Operation(summary = "Update Product Status", security = @SecurityRequirement(name = "bearerAuth"))
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto changeProductStatus(@PathVariable Long id) {
		return adminService.changeProductStatus(id);
	}

}
