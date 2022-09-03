package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;

@RequestMapping("/signup")
@Controller
public class SignupController {
	
	@Autowired
	private SignupService signupService;
	
	/**
	 * ユーザー登録画面へ遷移
	 */
	@GetMapping("/form")
	public String signup(Model model, @ModelAttribute SignupForm form, @ModelAttribute("message") String message) {
		
		return "signup/signupForm";
		
	}
	
	/**
	 * ユーザー登録画面へ遷移(確認画面から戻るボタンをクリックした場合)
	 */
	@PostMapping("/form")
	public String signupGoBack(Model model, @ModelAttribute SignupForm form) {
		
		return "signup/signupForm";
		
	}
	
	/**
	 * ユーザー登録確認画面へ遷移
	 */
	@PostMapping("/confirm")
	public String confirm(Model model, @Validated SignupForm form, BindingResult result) {
		
		if(result.hasErrors()) {
			// バリデーションエラーならばユーザー登録画面へ遷移
			return "signup/signupForm";
		}
		
		return "confirm/confirm";
		
	}
	
	/**
	 * ユーザー登録処理
	 */
	@PostMapping("/complete")
	public String signupComplete(Model model, @Validated SignupForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			
			// バリデーションエラーがあればユーザー登録画面へ遷移
			return "signup/signupForm";
			
		}
		
		User user = new User();
		user.setUser_id(form.getUser_id());
		user.setUser_name(form.getUser_name());
		user.setPassword(form.getPassword());
		user.setRole("USER");
		user.setCreated_at(LocalDateTime.now());
		
		signupService.save(user);
		redirectAttributes.addFlashAttribute("message", "登録しました。");
		
		return "redirect:/signup/form";
		
	}
}
