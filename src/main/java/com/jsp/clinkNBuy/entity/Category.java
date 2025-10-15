package com.jsp.clinkNBuy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(generator = "cat_id")
	@SequenceGenerator(name = "cat_id", initialValue = 1000, allocationSize = 1)
	private Long id;
	private String name;
}
