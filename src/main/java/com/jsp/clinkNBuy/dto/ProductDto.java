package com.jsp.clinkNBuy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	@NotEmpty(message = "Name is Required")
	private String name;
	@NotEmpty(message = "Description is Required")
	private String description;
	@NotNull(message = "Price is Required")
	private Double price;
	@NotNull(message = "Stock is Required")
	private Integer stock;
	@NotEmpty(message = "Image is Required")
	private String imageLink;
	@NotEmpty(message = "Category is Required")
	private String category;
	@NotEmpty(message = "Brand is Required")
	private String brand;
}
