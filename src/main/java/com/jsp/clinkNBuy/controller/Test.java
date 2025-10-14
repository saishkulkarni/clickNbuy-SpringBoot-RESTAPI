package com.jsp.clinkNBuy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@GetMapping("/test")
	public String testA() {
		return "This is Test";
	}

}
