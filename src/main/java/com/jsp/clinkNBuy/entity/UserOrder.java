package com.jsp.clinkNBuy.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class UserOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double totalCost;
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	@CreationTimestamp
	private LocalDateTime createdTime;
	private String paymentId;
	private String orderId;
	private String paymentStatus;
	@OneToMany(cascade = CascadeType.ALL)
	List<OrderItem> items;
	@ManyToOne
	User user;
}
