package com.jsp.clinkNBuy.service.impl;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.UserDao;
import com.jsp.clinkNBuy.dto.OtpDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.dto.UserDto;
import com.jsp.clinkNBuy.entity.Role;
import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.exception.DataExistsException;
import com.jsp.clinkNBuy.service.AuthService;
import com.jsp.clinkNBuy.util.EmailSender;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	UserDao userDao;
	PasswordEncoder encoder;
	EmailSender emailSender;

	@Override
	public ResponseDto register(UserDto userDto) {
		if (userDao.isEmailUnique(userDto.getEmail()) && userDao.isMobileUnique(userDto.getMobile())) {
			int otp = new Random().nextInt(100000, 1000000);
			emailSender.sendOtp(userDto.getEmail(), otp, userDto.getName());
			userDao.saveUser(new User(null, userDto.getName(), userDto.getEmail(),
					encoder.encode(userDto.getPassword()), userDto.getMobile(), null, otp,
					LocalDateTime.now().plusMinutes(5), Role.valueOf("ROLE_" + userDto.getRole().toUpperCase()), false));
			return new ResponseDto("Otp Sent Success, Verify within 5 minutes", userDto);
		} else {
			if (!userDao.isEmailUnique(userDto.getEmail()))
				throw new DataExistsException("Email Already Exists : " + userDto.getEmail());
			else
				throw new DataExistsException("Mobile Already Exists : " + userDto.getMobile());
		}
	}

	@Override
	public ResponseDto verifyOtp(OtpDto otpDto) throws TimeoutException {
		User user=userDao.findByEmail(otpDto.getEmail());
		if(LocalDateTime.now().isBefore(user.getOtpExpiryTime())) {
			if(otpDto.getOtp()==user.getOtp()) {
				user.setStatus(true);
				user.setOtp(0);
				user.setOtpExpiryTime(null);
				userDao.saveUser(user);
				return new ResponseDto("Account Created Success", user);
			}else {
				throw new InputMismatchException("Otp miss match, Try Again");
			}
		}else {
			throw new TimeoutException("Otp Expired, Resend Otp and Try Again");
		}
	}

}
