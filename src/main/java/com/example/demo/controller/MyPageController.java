package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class MyPageController {

	@Autowired
	UserService userService;

	@GetMapping("/mypage")
	public String showMypage(HttpSession session, Model model) {
		User loginUser = (User) session.getAttribute("loginUser");

		User user = userService.findById(loginUser.getId());

		//		List<Coupons> coupons = userService.getCoupons(user.getId());

		model.addAttribute("User", user);
		//		model.addAttribute("coupons", coupons);

		return "/mypage";

		//		 public List<OrderHistoryRow> findHistoryByUser(int id) {
		//		        return orderMapper.findHistoryByUser(id);
		//		    }

	}

}
