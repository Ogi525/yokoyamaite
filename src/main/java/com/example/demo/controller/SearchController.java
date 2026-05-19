package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.search.SearchService;

@Controller
public class SearchController {

	private final SearchService itemService;

	public SearchController(SearchService itemService) {
		this.itemService = itemService;
	}

	// 検索画面表示
	@GetMapping("/search")
	public String searchPage() {
		return "search/search";
	}

	// 検索実行
	@GetMapping("/searchresult")
	public String search(

			@RequestParam(required = false) String keyword,

			@RequestParam(required = false) List<Integer> areas,

			@RequestParam(required = false) List<Integer> categories,

			@RequestParam(required = false) Integer minPrice,

			@RequestParam(required = false) Integer maxPrice,

			@RequestParam(required = false) String sort,

			Model model) {

		List<Product> products = itemService.search(
				keyword,
				areas,
				categories,
				minPrice,
				maxPrice,
				sort);

		model.addAttribute("products", products);

		return "search/searchresult";
	}

}