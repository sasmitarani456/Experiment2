package com.hutech.payrollapp.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PaySlipController {

	@GetMapping("/paySlip")
	public ModelAndView payslipPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("payslip");
		return mv;
	}
}
