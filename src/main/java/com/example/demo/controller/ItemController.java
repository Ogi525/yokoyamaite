package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Product;
import com.example.demo.service.CommentService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

@Controller
public class ItemController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/item/{id}")
	public String showItem(
			@PathVariable Integer id,
			Model model) {

		// 商品取得
		Product product = productService.findById(id);

		// HTMLへ渡す
		model.addAttribute("product", product);

		// レビュー一覧
		model.addAttribute(
				"reviews",
				reviewService.findByProductId(id));

		// コメント一覧
		model.addAttribute(
				"comments",
				commentService.findByProductId(id));

		return "temp/item";
	}
}