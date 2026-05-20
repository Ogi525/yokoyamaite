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
		// セッションからログイン中ユーザーID取得
		Integer userId = (Integer) session.getAttribute("userId");

		// 未ログインならログイン画面へ
		if (userId == null) {
			return "redirect:/login";
		}

		// セッションからカート情報取得
		List<CartItem> cart = cartService.getCart(session);

		// カート内の商品を1つずつ確認
		for (CartItem item : cart) {

			// 商品IDと数量を渡して在庫が足りるか確認
			boolean hasStock = orderService.hasStock(
					item.getProductId(),
					item.getQuantity());

			// 在庫不足なら決済画面へ戻す
			if (!hasStock) {

				model.addAttribute(
						"errorMessage",
						"在庫切れの商品があります。");

				return "cart/payment";
			}
		}

		// 在庫確認をすべて通過したら
		// 実際にDBの在庫数を減らす
		for (CartItem item : cart) {

			orderService.decreaseStock(
					item.getProductId(),
					item.getQuantity());
		}

		// 合計金額取得
		Integer grandTotal = (Integer) session.getAttribute("grandTotal");

		// 注文保存
		Date orderDate = new Date(System.currentTimeMillis());
		orderService.saveOrder(
				userId,
				cart,
				grandTotal,
				orderDate);

		//くーぽんを使用済みにする
		orderService.useCoupon(
				couponId,
				userId);

		// =========================
		// カートを空にする
		cartService.clearCart(session);

		// 購入完了画面へ
		return "cart/complete";
	}
}