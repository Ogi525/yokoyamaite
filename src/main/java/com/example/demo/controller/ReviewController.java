package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.form.ReviewForm;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

@Controller
public class ReviewController {

	private final ReviewService reviewService;
	private final ProductService productService;

	public ReviewController(ReviewService reviewService, ProductService productService) {
		this.reviewService = reviewService;
		this.productService = productService;
	}

	@PostMapping("/item/{productId}/reviews")
	public String postReview(
			@PathVariable Integer productId,
			@Valid @ModelAttribute("reviewForm") ReviewForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		// ログインユーザー取得
		User loginUser = (User) session.getAttribute("loginUser");

		// 未ログイン
		if (loginUser == null) {

			session.setAttribute(
					"redirectAfterLogin",
					"/item/" + productId);

			return "redirect:/login";
		}

		// バリデーションエラー
		if (bindingResult.hasErrors()) {
			model.addAttribute("product", productService.findById(productId));
			model.addAttribute("reviews", reviewService.findByProductId(productId));
			return "temp/item";
		}

		// 購入済み判定
		if (!reviewService.hasPurchased(loginUser.getId(), productId)) {
			model.addAttribute("product", productService.findById(productId));
			model.addAttribute("reviews", reviewService.findByProductId(productId));
			model.addAttribute("reviewError", "購入済み商品のみレビューできます");
			return "temp/item";
		}

		// 重複レビュー判定
		if (reviewService.hasReviewed(loginUser.getId(), productId)) {
			model.addAttribute("product", productService.findById(productId));
			model.addAttribute("reviews", reviewService.findByProductId(productId));
			model.addAttribute("reviewError", "すでにレビュー済みです");
			return "temp/item";
		}

		// Review作成
		Review review = new Review();

		review.setUserId(loginUser.getId());

		review.setProductId(productId);

		review.setRating(form.getRating());

		review.setComment(form.getComment());

		// 保存
		reviewService.saveReview(review);

		return "redirect:/item/" + productId;
	}
}