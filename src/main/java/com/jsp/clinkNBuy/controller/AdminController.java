package com.jsp.clinkNBuy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Category;
import com.jsp.clinkNBuy.service.AdminService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

	AdminService adminService;

	@PostMapping("/category")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseDto addCategory(@RequestBody Category category) {
		return adminService.addCategory(category);
	}

	@GetMapping("/category")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto viewCategories() {
		return adminService.viewCategories();
	}

	@DeleteMapping("/category/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseDto deleteCategory(@PathVariable Long id) {
		return adminService.deleteCategory(id);
	}

	@PutMapping("/category/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseDto updateCategory(@PathVariable Long id, @RequestBody Category category) {
		return adminService.updateCategory(id, category);
	}

}
