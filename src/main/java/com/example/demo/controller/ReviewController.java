package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.form.ReviewForm;
import com.example.demo.service.CouponService;
import com.example.demo.service.ReviewService;

@Controller
public class ReviewController {

	private final ReviewService reviewService;
	private final CouponService couponService;

	public ReviewController(
			ReviewService reviewService,
			CouponService couponService) {

		this.reviewService = reviewService;
		this.couponService = couponService;
	}

	@PostMapping("/item/{productId}/reviews")
	public String postReview(
			@PathVariable Integer productId,
			@Valid @ModelAttribute("reviewForm") ReviewForm form,
			BindingResult bindingResult,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {

			session.setAttribute(
					"redirectAfterLogin",
					"/item/" + productId);

			return "redirect:/login";
		}

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute(
					"reviewError",
					"評価とコメントを正しく入力してください");

			return "redirect:/item/" + productId + "?tab=review";
		}

		boolean purchased = reviewService.hasPurchased(
				loginUser.getId(),
				productId);

		if (!purchased) {
			redirectAttributes.addFlashAttribute(
					"reviewError",
					"購入していない商品はレビューできません");

			return "redirect:/item/" + productId + "?tab=review";
		}

		boolean reviewed = reviewService.hasReviewed(
				loginUser.getId(),
				productId);

		if (reviewed) {
			redirectAttributes.addFlashAttribute(
					"reviewError",
					"すでにレビュー済みです");

			return "redirect:/item/" + productId + "?tab=review";
		}

		Review review = new Review();

		review.setUserId(loginUser.getId());
		review.setProductId(productId);
		review.setRating(form.getRating());
		review.setComment(form.getComment());

		reviewService.saveReview(review);

		if (form.getComment() != null && form.getComment().length() >= 100) {

			couponService.issueReviewCoupon(loginUser.getId());

			redirectAttributes.addFlashAttribute(
					"couponMessage",
					"100文字以上のレビュー投稿ありがとうございます。500円クーポンを発行しました！");
		}

		redirectAttributes.addFlashAttribute(
				"reviewSuccess",
				"レビューを投稿しました。");

		return "redirect:/item/" + productId + "?tab=review";
	}
}