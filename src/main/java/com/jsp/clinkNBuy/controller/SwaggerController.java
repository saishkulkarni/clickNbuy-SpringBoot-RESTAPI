package com.jsp.clinkNBuy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {
	@GetMapping("/")
	public String loadSwagger() {
		return "redirect:swagger-ui.html";
	}
}
