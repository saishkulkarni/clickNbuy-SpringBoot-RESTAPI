package com.jsp.clinkNBuy.controller;

import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.clinkNBuy.dto.LoginDto;
import com.jsp.clinkNBuy.dto.OtpDto;
import com.jsp.clinkNBuy.dto.PasswordDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.dto.UserDto;
import com.jsp.clinkNBuy.service.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/auth")
public class AuthController {

	AuthService authService;

	@PostMapping("/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseDto register(@Valid @RequestBody UserDto userDto) {
		return authService.register(userDto);
	}

	@PostMapping("/verify-otp")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseDto verifyOtp(@RequestBody OtpDto otpDto) throws TimeoutException {
		return authService.verifyOtp(otpDto);
	}

	@GetMapping("/resend-otp")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseDto resendOtp(@RequestParam String email) {
		return authService.resendOtp(email);
	}

	@GetMapping("/forgot-password")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDto forgotPassword(@RequestParam String email) {
		return authService.forgotPassword(email);
	}

	@PostMapping("/forgot-password")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDto forgotPassword(@Valid @RequestBody PasswordDto passwordDto) throws TimeoutException {
		return authService.forgotPassword(passwordDto);
	}

	@PostMapping("/login")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDto login(@Valid @RequestBody LoginDto loginDto) {
		return authService.login(loginDto);
	}
}
