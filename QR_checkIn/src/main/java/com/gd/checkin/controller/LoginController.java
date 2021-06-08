package com.gd.checkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gd.checkin.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	@Autowired LoginService loginService;
	
	@GetMapping("/login")
	public String login(Model model) {
		String token ="";
		
		try {
			token = loginService.QRcreate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("token",token);
		return "/login";
	}
}