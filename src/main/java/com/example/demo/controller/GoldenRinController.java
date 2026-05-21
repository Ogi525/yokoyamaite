package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoldenRinController {

	@GetMapping("/golden-rin/event")
	public String showGoldenRinEvent(HttpSession session) {
		session.setAttribute("goldenAccess", true);
		return "event/golden-rin";
	}
}