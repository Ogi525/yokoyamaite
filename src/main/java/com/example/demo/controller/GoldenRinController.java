package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoldenRinController {

	@GetMapping("/golden-rin/event")
	public String showGoldenRinEvent() {
		return "event/golden-rin";
	}
}
