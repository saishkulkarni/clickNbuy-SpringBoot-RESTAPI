package com.jsp.clinkNBuy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {
	@GetMapping("/")
	public String loadSwagger() {
		return "redirect:swagger-ui.html";
	}
}
