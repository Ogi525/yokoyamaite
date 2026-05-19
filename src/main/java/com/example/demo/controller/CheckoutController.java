
package com.example.demo.controller;

import java.time.YearMonth;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.CartService;
import com.example.demo.service.CheckoutService;
import com.example.demo.service.OrderService;

@Controller
public class CheckoutController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CheckoutService checkoutService;

	// 決済画面表示
	@GetMapping("/checkout")
	public String showPayment(HttpSession session, Model model) {

		// ログイン確認
		//		Object loginUserObj = session.getAttribute("loginUser");
		Integer userId = (Integer) session.getAttribute("userId");

		//実際使うやつ
		//		if (loginUserObj == null) {
		//
		//			// ログイン後に戻る先を保存
		//			session.setAttribute("redirectAfterLogin", "/checkout");
		//
		//			return "redirect:/login";
		//		}
		//あとで消すやつ
		if (userId == null) {

			// ログイン後に戻る先を保存
			session.setAttribute("redirectAfterLogin", "/checkout");

			return "redirect:/login";
		}
		//あとで使う
		//		User loginUser = (User) loginUserObj;
		//				Integer userId = loginUser.getId();

		// カート合計金額（セッション保存してる想定）
		Integer totalPrice = (Integer) session.getAttribute("totalPrice");

		if (totalPrice == null) {
			totalPrice = 0;
		}

		// 使えるクーポン一覧
		model.addAttribute("coupons",
				checkoutService.getAvailableCoupons(userId, totalPrice));

		// 初期表示用
		model.addAttribute("discountAmount", 0);
		model.addAttribute("finalPrice", totalPrice);
		model.addAttribute("totalPrice", totalPrice);

		return "cart/payment";
	}

	// 最終確認へ進む
	@PostMapping("/payment/order")
	public String confirmPayment(
			@RequestParam String paymentMethod,

			@RequestParam(required = false) String deliveryType,
			@RequestParam(required = false) String postalCode,
			@RequestParam(required = false) String otherAddress,
			@RequestParam(required = false) String otherName,

			@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) Integer expireMonth,
			@RequestParam(required = false) Integer expireYear,
			@RequestParam(required = false) Integer couponId,

			HttpSession session,
			Model model) {

		// 配送先が未選択なら自宅扱い
		if (deliveryType == null) {
			deliveryType = "home";
		}

		// 別住所配送なのに住所未入力ならエラー
		if ("other".equals(deliveryType)) {
			if (postalCode == null || postalCode.isBlank()
					|| otherAddress == null || otherAddress.isBlank()
					|| otherName == null || otherName.isBlank()) {

				model.addAttribute("errorMessage", "別住所に配送する場合は、住所情報を入力してください。");
				model.addAttribute("deliveryType", deliveryType);

				return "cart/payment";
			}
		}

		// クレジットカード選択時だけカード情報チェック
		if ("クレジットカード".equals(paymentMethod)) {
			if (cardNumber == null || cardNumber.isBlank()
					|| expireMonth == null
					|| expireYear == null) {

				model.addAttribute("errorMessage", "クレジットカード情報を入力してください。");
				model.addAttribute("deliveryType", deliveryType);

				return "cart/payment";
			}
			// 有効期限チェック
			YearMonth now = YearMonth.now();
			YearMonth cardExpire = YearMonth.of(expireYear, expireMonth);

			if (cardExpire.isBefore(now)) {

				model.addAttribute("errorMessage", "クレジットカードの有効期限が切れています。");
				model.addAttribute("deliveryType", deliveryType);

				return "cart/payment";
			}
		}

		//後で使う
		//		User loginUser = (User) session.getAttribute("loginUser");
		//		Integer userId = loginUser.getId();

		//消すやつ
		Integer userId = (Integer) session.getAttribute("userId");

		Integer totalPrice = (Integer) session.getAttribute("totalPrice");

		if (totalPrice == null) {
			totalPrice = 0;
		}

		// 割引額取得
		Integer discountAmount = checkoutService.getDiscountAmount(couponId, userId, totalPrice);

		// 送料計算
		Integer shippingFee = checkoutService.calculateShippingFee(totalPrice, discountAmount);

		// 最終支払額
		Integer grandTotal = checkoutService.calculateGrandTotal(
				totalPrice,
				discountAmount,
				shippingFee);

		// 確認画面に渡す
		model.addAttribute("paymentMethod", paymentMethod);
		model.addAttribute("deliveryType", deliveryType);
		model.addAttribute("postalCode", postalCode);
		model.addAttribute("otherAddress", otherAddress);
		model.addAttribute("otherName", otherName);
		model.addAttribute("couponId", couponId);
		model.addAttribute("discountAmount", discountAmount);
		model.addAttribute("shippingFee", shippingFee);
		model.addAttribute("finalPrice", grandTotal);
		model.addAttribute("totalPrice", totalPrice);

		return "cart/order";
	}

}