package com.jsp.clinkNBuy.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailSender {

	JavaMailSender mailSender;

	@Async
	public void sendOtp(String email, int otp, String name) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(email);
			helper.setSubject("Verify Your Click N Buy Account");

			String htmlContent = """
					<html>
					<body style="font-family: Arial, sans-serif;">
					    <h2>Welcome to Click N Buy!</h2>
					    <p>Hello %s,</p>
					    <p>Your verification code is: <strong style="font-size: 24px; color: #007bff;">%d</strong></p>
					    <p>This code will expire in 5 minutes.</p>
					    <p>If you didn't request this, please ignore this email.</p>
					</body>
					</html>
					""".formatted(name, otp);

			helper.setText(htmlContent, true);
			mailSender.send(message);
		} catch (Exception e) {
			System.err.println("Email failed. OTP: " + otp);
		}
	}

	@Async
	public void sendForgotOtp(String email, int otp, String name) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Otp for Updating Password");
			message.setText("Hello " + name + " , Your OTP is " + otp
					+ " , It is valid only for 5 mins, You can use this for Updating Password");
			mailSender.send(message);
		} catch (Exception e) {
			System.err.println("The OTP is :" + otp);
		}
	}
}
