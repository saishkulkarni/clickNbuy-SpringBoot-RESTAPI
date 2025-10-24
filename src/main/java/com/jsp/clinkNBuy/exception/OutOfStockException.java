package com.jsp.clinkNBuy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OutOfStockException extends RuntimeException {
	String message="Out of Stock";
}
