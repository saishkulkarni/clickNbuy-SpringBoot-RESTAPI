package com.jsp.clinkNBuy.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailSender {

	JavaMailSender mailSender;

	public void sendOtp(String email, int otp, String name) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Otp for Creating Account with Click N Buy");
			message.setText("Hello " + name + " Thank you for Creating an account with us , Your OTP is " + otp+" , It is valid only for 5 mins");
			mailSender.send(message);
		} catch (Exception e) {
			System.err.println("The OTP is :" + otp);
		}
	}
}
