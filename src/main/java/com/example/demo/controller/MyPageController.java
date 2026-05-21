package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.History;
import com.example.demo.entity.User;
import com.example.demo.service.CouponService;
import com.example.demo.service.HistoryService;
import com.example.demo.service.UserService;

@Controller
public class MyPageController {

	@Autowired
	UserService userService;

	@Autowired
	CouponService couponService;

	@Autowired
	HistoryService historyService;

	@GetMapping("/mypage")
	public String showMypage(HttpSession session, Model model) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}

		User user = userService.findByEmail(loginUser.getEmail());

		//クーポンを表示する
		List<Coupon> coupons = couponService.findByUserId(user.getId());

		//購入履歴を表示する
		List<History> histories = historyService.findByUserId(user.getId());

		System.out.println("ログインユーザーID：" + user.getId());
		System.out.println("購入履歴件数：" + histories.size());
		System.out.println(histories);

		model.addAttribute("user", user);
		model.addAttribute("coupons", coupons);
		model.addAttribute("histories", histories);

		return "mypage/mypage";

	}

}