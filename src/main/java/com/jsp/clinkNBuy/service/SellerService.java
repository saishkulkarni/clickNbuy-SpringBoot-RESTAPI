package com.jsp.clinkNBuy.service;

import java.security.Principal;

import com.jsp.clinkNBuy.dto.ProductDto;
import com.jsp.clinkNBuy.dto.ResponseDto;

public interface SellerService {
	ResponseDto saveProduct(ProductDto dto, Principal principal);

	ResponseDto getProducts(Principal principal);

	ResponseDto deleteProduct(Long id, Principal principal);
}
