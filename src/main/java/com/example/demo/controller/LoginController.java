package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.form.SignupForm;

@Controller
public class LoginController {
	
	/**
	 * ログイン画面へ遷移
	 */
	@GetMapping("/login")
	public String getLogin() {
		
		return "login/login";
		
	}
	
	/**
	 * タスク画面へリダイレクト
	 */
	@PostMapping("/login")
	public String postLogin() {
		
		return "redirect:/task/list";
		
	}
	
}
