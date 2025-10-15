package com.jsp.clinkNBuy.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private Long mobile;
	@CreationTimestamp
	private LocalDateTime createdTime;
	@JsonIgnore
	private int otp;
	@JsonIgnore
	private LocalDateTime otpExpiryTime;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean status;
	@JsonIgnore
	private int otpAttempts;
	@JsonIgnore
	private LocalDateTime lastOtpRequestTime;
}
