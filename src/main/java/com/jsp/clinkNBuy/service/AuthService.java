package com.jsp.clinkNBuy.service;

import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.dto.UserDto;

public interface AuthService {

	ResponseDto register(UserDto userDto);

}
