package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Product;
import com.example.demo.form.ReviewForm;
import com.example.demo.service.PostService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

@Controller
public class ItemController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ReviewService reviewService;

	//	@Autowired
	//	private CommentService commentService;

	@Autowired
	private PostService postService;

	@GetMapping("/item/{id}")
	public String showItem(
			@PathVariable Integer id,
			Model model,
			HttpSession session) {

		// 商品取得
		Product product = productService.findById(id);
		if (Boolean.TRUE.equals(product.getHidden())) {

			Boolean goldenAccess = (Boolean) session.getAttribute("goldenAccess");

			if (goldenAccess == null || !goldenAccess) {

				return "redirect:/";
			}

			/* 一回見たら権限削除 */
			session.removeAttribute("goldenAccess");
		}

		// HTMLへ渡す
		model.addAttribute("product", product);

		// レビュー一覧
		model.addAttribute(
				"reviews",
				reviewService.findByProductId(id));

		// コメント一覧
		model.addAttribute(
				"comments",
				postService.getTreePostsByProduct(id));
		model.addAttribute("productId", id);
		model.addAttribute("reviewForm", new ReviewForm());
		return "temp/item";
	}

}