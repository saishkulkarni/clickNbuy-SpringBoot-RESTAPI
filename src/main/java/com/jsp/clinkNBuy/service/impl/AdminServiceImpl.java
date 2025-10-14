package com.jsp.clinkNBuy.service.impl;

import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.AdminDao;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Category;
import com.jsp.clinkNBuy.exception.DataExistsException;
import com.jsp.clinkNBuy.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	AdminDao adminDao;

	@Override
	public ResponseDto addCategory(Category category) {
		if (adminDao.isCategoryUnique(category.getName())) {
			adminDao.save(category);
			return new ResponseDto("Category Added Success", category);
		} else
			throw new DataExistsException(category.getName() + " Already Present");
	}

	@Override
	public ResponseDto viewCategories() {
		return new ResponseDto("Found Success", adminDao.findAllCategory());
	}

	@Override
	public ResponseDto deleteCategory(Long id) {
		adminDao.deleteCategory(id);
		return new ResponseDto("Deleted Success", null);
	}

	@Override
	public ResponseDto updateCategory(Long id, Category req) {
		Category category = adminDao.findCategoryById(id);
		category.setName(req.getName());
		adminDao.save(category);

		return new ResponseDto("Category Updated Success", category);
	}

}
