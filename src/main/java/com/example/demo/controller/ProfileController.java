package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile/edit")
	public String editProfile(HttpSession session, Model model) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}

		// 最新データ取得
		User user = userService.findByEmail(loginUser.getEmail());

		model.addAttribute("user", user);

		return "profile/edit";
	}

	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute User formUser, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}

		//emailの重複チェック
		User duplicate = userService.findByEmail(formUser.getEmail());

		if (duplicate != null && duplicate.getId() != loginUser.getId()) {
			session.setAttribute("errorMessage", "このメールアドレスはすでに使用されています");
			return "profile/edit";
		}

		// IDセット
		formUser.setId(loginUser.getId());

		if (formUser.getPassword() == null || formUser.getPassword().isEmpty()) {
			formUser.setPassword(loginUser.getPassword()); // 変更なし
		}

		// 更新処理
		userService.updateUser(formUser);

		// セッション更新
		session.setAttribute("loginUser", formUser);

		return "redirect:/mypage";
	}

	@PostMapping("/profile/confirm")
	public String confirmProfile(@ModelAttribute User formUser, Model model, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}

		// ID検索→保持
		formUser.setId(loginUser.getId());

		// パスワード空なら元の値
		if (formUser.getPassword() == null || formUser.getPassword().isEmpty()) {
			formUser.setPassword(loginUser.getPassword());
		}

		model.addAttribute("user", formUser);

		return "profile/confirm";
	}

}
