package com.jsp.clinkNBuy.service;

import java.security.Principal;

import com.jsp.clinkNBuy.dto.ProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Product;

public interface SellerService {
	ResponseDto saveProduct(ProductDto dto, Principal principal);

	ResponseDto getProducts(Principal principal, int page, int size, String sort, boolean desc);

	ResponseDto deleteProduct(Long id, Principal principal);

	ResponseDto addProducts(Principal principal);

	ResponseDto updateProduct(Long id, Product product, Principal principal);
}
