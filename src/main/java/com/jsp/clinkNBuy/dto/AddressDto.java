package com.jsp.clinkNBuy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
	private String houseNo;
	private String landmark;
	private String street;
	private String area;
	private String city;
	private Long pincode;
}
