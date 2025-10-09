package com.jsp.clinkNBuy.service;

import java.util.concurrent.TimeoutException;

import com.jsp.clinkNBuy.dto.OtpDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.dto.UserDto;

public interface AuthService {

	ResponseDto register(UserDto userDto);

	ResponseDto verifyOtp(OtpDto otpDto) throws TimeoutException;

}
