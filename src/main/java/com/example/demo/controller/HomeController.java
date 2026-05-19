package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	/**
	 * トップページ表示
	 */
	@GetMapping("/")
	public String index(Model model) {

		// おすすめ商品一覧
		List<Product> recommendProducts = productService.findRecommendProducts();

		// 新着商品一覧
		List<Product> newProducts = productService.findNewProducts();

		// ランキング商品一覧
		List<Product> rankingProducts = productService.findRankingProducts();

		// Modelに格納
		model.addAttribute("recommendProducts", recommendProducts);
		model.addAttribute("newProducts", newProducts);
		model.addAttribute("rankingProducts", rankingProducts);

		return "auth/home";
	}
}
