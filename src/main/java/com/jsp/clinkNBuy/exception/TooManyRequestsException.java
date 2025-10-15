package com.jsp.clinkNBuy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TooManyRequestsException extends RuntimeException {
	private String message = "Too Many Requests";
}
