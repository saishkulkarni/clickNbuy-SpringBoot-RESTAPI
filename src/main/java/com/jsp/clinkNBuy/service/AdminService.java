package com.jsp.clinkNBuy.service;

import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Category;

public interface AdminService {

	ResponseDto addCategory(Category category);

	ResponseDto viewCategories();

	ResponseDto deleteCategory(Long id);

	ResponseDto updateCategory(Long id, Category category);

}
