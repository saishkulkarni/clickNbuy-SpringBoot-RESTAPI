package com.jsp.clinkNBuy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountNotVerifiedException extends RuntimeException {
	private String message;
}
