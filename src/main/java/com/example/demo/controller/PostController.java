package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.PostForm;
import com.example.demo.service.PostService;

@Controller
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	// 一覧表示
	@GetMapping("/forum/{productId}")
	public String forumPage(@PathVariable Integer productId, Model model) {
		try {
			model.addAttribute("posts", postService.getTreePostsByProduct(productId));
			model.addAttribute("productId", productId);
			return "post/forum";
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error/notFound";
		}

	}

	// 投稿
	@PostMapping("/post")
	public String post(@Valid @ModelAttribute PostForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			return "redirect:/login";
		}
		// バリデーションエラー
		if (bindingResult.hasErrors()) {
			model.addAttribute("posts", postService.getTreePostsByProduct(form.getProductId()));
			model.addAttribute("productId", form.getProductId());
			model.addAttribute("errors", bindingResult.getAllErrors());
			return "post/forum";
		}

		try {
			postService.addPost(userId, form.getProductId(), form.getBody(), form.getParentId());
		} catch (IllegalArgumentException e) {
			// 不正なproductId・parentId
			model.addAttribute("errorMessage", e.getMessage());
			return "error/notFound";
		}

		return "redirect:/forum/" + form.getProductId();
	}

	@PostMapping("/post/{postId}/delete")
	public String delete(@PathVariable Integer postId,
			@RequestParam Integer productId,
			HttpSession session,
			Model model) {

		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/login";
		}

		try {
			postService.deletePost(postId, userId);
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error/notFound";
		} catch (IllegalStateException e) {
			// 権限なし
			model.addAttribute("errorMessage", e.getMessage());
			return "error/forbidden";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "削除に失敗しました。もう一度お試しください。");
			return "error/serverError";
		}
		//forumをあとでproductに変更
		return "redirect:/forum/" + productId;
	}
}
