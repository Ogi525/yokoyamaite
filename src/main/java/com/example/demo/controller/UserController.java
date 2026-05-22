package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserMapper userMapper, UserService userService) {
		this.userMapper = userMapper;
		this.userService = userService;
	}

	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "register/register";
	}

	@PostMapping("/register")
	public String submitForm(
			@Valid @ModelAttribute("userForm") UserForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		// 入力エラーがある場合
		if (bindingResult.hasErrors()) {
			return "register/register";
		}

		// パスワード確認チェック
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			bindingResult.rejectValue(
					"confirmPassword",
					"password.mismatch",
					"パスワードが一致しません");

			return "register/register";
		}

		// メールアドレス重複チェック
		User existingUser = userMapper.findByEmail(form.getEmail());

		if (existingUser != null) {
			bindingResult.rejectValue(
					"email",
					"email.duplicate",
					"このメールアドレスはすでに登録されています");

			return "register/register";
		}

		// ユーザー登録
		userService.register(form);

		// 登録後にログイン状態にする
		User loginUser = userMapper.findByEmail(form.getEmail());

		session.setAttribute("loginUser", loginUser);

		return "redirect:/mypage";
	}
}