package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvatarController {

	@GetMapping(value = "/avatar/{name}", produces = "image/svg+xml")
	@ResponseBody
	public String avatar(@PathVariable String name) {

		String initial = name.substring(0, 1);

		return """
				<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
				    <circle cx="50" cy="50" r="50" fill="#4F46E5"/>
				    <text x="50%" y="55%"
				          font-size="40"
				          text-anchor="middle"
				          fill="white"
				          font-family="sans-serif">
				        %s
				    </text>
				</svg>
				""".formatted(initial);
	}
}