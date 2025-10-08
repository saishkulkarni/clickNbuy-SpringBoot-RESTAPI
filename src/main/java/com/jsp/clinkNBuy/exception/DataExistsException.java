package com.jsp.clinkNBuy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataExistsException extends RuntimeException {
	String message = "Data Already Exists";
}
