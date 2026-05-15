package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.search.SerchService;

@Controller
@RequestMapping("/serch")
public class SearchController {

	private final SerchService itemService;

	public SearchController(SerchService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public String search(

			@RequestParam(required = false) String keyword,

			@RequestParam(required = false) String area,

			@RequestParam(required = false) String category,

			@RequestParam(required = false) Integer minPrice,

			@RequestParam(required = false) Integer maxPrice,

			@RequestParam(required = false) String sort,

			Model model) {

		List<Item> items = itemService.search(
				keyword,
				area,
				category,
				minPrice,
				maxPrice,
				sort);

		model.addAttribute("items", items);

		return "items";
	}
}
