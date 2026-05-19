package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.UserForm;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("form", new UserForm());
		return "/register";
	}

	@PostMapping("/register")
	public String submitForm(
			@Validated @ModelAttribute("form") UserForm form,
			BindingResult result,
			Model model) {

		// メールアドレス重複チェック
		if (userService.existsByEmail(form.getEmail())) {

			result.rejectValue(
					"email",
					"duplicate",
					"このメールアドレスは既に使用されています");
		}
		// パスワード一致チェック
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			model.addAttribute("registerError", "パスワードが一致しません");
			return "/register";
		}

		// エラーがある場合は登録画面に戻す
		if (result.hasErrors()) {
			return "/register";
		}

		// 登録処理
		userService.register(form);

		return "/result";
	}

}
