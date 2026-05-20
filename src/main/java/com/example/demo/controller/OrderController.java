package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	// 注文確定処理
	@PostMapping("/cart/complete")
	public String completeOrder(
			@RequestParam(required = false) Integer couponId,
			HttpSession session,
			Model model) {

		// ログインユーザー取得
		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			return "redirect:/login";
		}

		Integer userId = user.getId();

		// カート取得
		List<CartItem> cart = cartService.getCart(session);

		// カートが空チェック
		if (cart.isEmpty()) {
			model.addAttribute("errorMessage", "カートが空です");
			return "cart/payment";
		}

		// ★ここで合計金額を計算（これが正）
		int grandTotal = cart.stream()
				.mapToInt(item -> item.getPrice() * item.getQuantity())
				.sum();

		// 在庫チェック
		for (CartItem item : cart) {
			boolean hasStock = orderService.hasStock(
					item.getProductId(),
					item.getQuantity());

			if (!hasStock) {
				model.addAttribute("errorMessage", "在庫切れの商品があります。");
				return "cart/payment";
			}
		}

		// 在庫減らす
		for (CartItem item : cart) {
			orderService.decreaseStock(
					item.getProductId(),
					item.getQuantity());
		}

		// クーポン取得（sessionから）
		Integer sessionCouponId = (Integer) session.getAttribute("couponId");

		// 注文保存
		Date orderDate = new Date(System.currentTimeMillis());

		orderService.saveOrder(
				userId,
				cart,
				grandTotal,
				orderDate);

		// クーポン使用処理（null対策）
		if (sessionCouponId != null) {
			orderService.useCoupon(sessionCouponId, userId);
		}

		// カート削除
		cartService.clearCart(session);

		return "cart/complete";
	}
}