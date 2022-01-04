package com.hutech.payrollapp.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	
	  @GetMapping(value = "/redirect")
	  public ModelAndView method() { String
	  redirectUrl = "https://upbeat-joliot-2020a0.netlify.app/"; return new
	  ModelAndView("redirect:" + redirectUrl); }
	  
	  @GetMapping(value = "/hhlogin") 
	  public void method(HttpServletResponse
	  httpServletResponse) { String redirectUrl =
	  "https://upbeat-joliot-2020a0.netlify.app/";
	  httpServletResponse.setHeader("Location", redirectUrl);
	  httpServletResponse.setStatus(302); }
	 
}
