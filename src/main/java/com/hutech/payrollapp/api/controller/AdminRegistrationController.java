package com.hutech.payrollapp.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hutech.payrollapp.api.model.Admin;
import com.hutech.payrollapp.api.service.AdminService;

@Controller
@RequestMapping("/registration")
public class AdminRegistrationController {
	private AdminService adminService;

	public AdminRegistrationController(AdminService adminService) {
		super();
		this.adminService = adminService;
	}

	@ModelAttribute("user")
	public Admin admin() {
		return new Admin();
	}

	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") Admin admin) {
		adminService.save(admin);
		return "redirect:/registration?success";
	}
}
