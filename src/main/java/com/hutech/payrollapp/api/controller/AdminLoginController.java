package com.hutech.payrollapp.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
	
	@GetMapping("/adminlogin")
	public String login() {
		return "adminLogin";
	}
	
	@GetMapping("/success")
	public String home() {
		return "index";
	}
}
