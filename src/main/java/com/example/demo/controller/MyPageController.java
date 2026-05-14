package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.User;

@Controller
public class MyPageController {

	@Autowired
	UserService userService;

	@GetMapping("/mypage")
	public String showMypage(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");

		User user = userService.findById(loginUser.getId());

		List<Coupon> coupons = userService.getCoupons(user.getId());

		List<userHistory> histories = userService.getHistory(user.getId());

		model.addAttribute("User", user);
		model.addAttribute("coupons", coupons);
		model.addAttribute("histories", histories);

		return "/mypage";

	}

}
