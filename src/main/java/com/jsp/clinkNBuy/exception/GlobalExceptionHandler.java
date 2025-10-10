package com.jsp.clinkNBuy.exception;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.clinkNBuy.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDto handle(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<String, String>();
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return new ErrorDto(errors);
	}

	@ExceptionHandler(DataExistsException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ErrorDto handle(DataExistsException exception) {
		return new ErrorDto(exception.getMessage());
	}

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorDto handle(DataNotFoundException exception) {
		return new ErrorDto(exception.getMessage());
	}

	@ExceptionHandler(TimeoutException.class)
	@ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
	public ErrorDto handle(TimeoutException exception) {
		return new ErrorDto(exception.getMessage());
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDto handle(NullPointerException exception) {
		return new ErrorDto("Sorry, Something Went Wrong, WE will fix it");
	}

	@ExceptionHandler(InputMismatchException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDto handle(InputMismatchException exception) {
		return new ErrorDto(exception.getMessage());
	}
}
