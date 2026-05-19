package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final ProductMapper productMapper;

	public CartController(CartService cartService, ProductMapper productMapper) {
		this.cartService = cartService;
		this.productMapper = productMapper;
	}

	/** カート一覧を表示する、送料の計算をする */
	@GetMapping
	public String showCart(HttpSession session, Model model) {
		List<CartItem> cart = cartService.getCart(session);
		int total = cart.stream().mapToInt(CartItem::getSubtotal).sum();

		/**カートがカラかどうかを判定する*/
		//		あとではずす
		//		boolean cartEmpty = cartService.isCartEmpty(session);
		//		model.addAttribute("cartEmpty", cart.isEmpty());

		//合計の計算

		int totalPrice = 0;

		for (CartItem item : cart) {
			totalPrice += item.getPrice() * item.getQuantity();
		}

		model.addAttribute("cart", cart);
		model.addAttribute("totalPrice", total);
		model.addAttribute("finalTotalPrice", totalPrice);
		return "cart/index";
	}

	/** カートに商品を追加する */
	@PostMapping("/add")
	public String addToCart(@RequestParam("productId") int productId,
			HttpSession session) {
		Product product = productMapper.findById(productId);
		if (product != null) {
			cartService.addItem(session, product);
		}
		return "redirect:/index";
	}

	/** カートから商品を削除する */
	@PostMapping("/remove")
	public String removeFromCart(@RequestParam("productId") int productId,
			HttpSession session) {
		cartService.removeItem(session, productId);
		return "redirect:/index";
	}

	/**カートの商品を増やす*/
	@PostMapping("/increment")
	public String incrementFromCart(
			@RequestParam("productId") int productId,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		boolean success = cartService.incrementQuantity(
				session,
				productId);

		// 在庫不足
		if (!success) {

			redirectAttributes.addFlashAttribute(
					"errorMessage",
					"在庫数を超えて追加できません");
		}

		return "redirect:/index";
	}

	/**カートの商品を減らす*/
	@PostMapping("/decrement")
	public String decrementFromCart(@RequestParam("productId") int productId,
			HttpSession session) {
		cartService.decrementQuantity(session, productId);
		return "redirect:/index";
	}

	//	/**クーポン関連*/
	//	@GetMapping("/cart")
	//	public String showCart(Model model) {
	//
	////		Integer userId = 1;
	//
	//		List<Coupon> coupons = CheckoutService.getAvailableCoupons(userId);
	//
	//		model.addAttribute("coupons", coupons);
	//
	//		return "cart";
	//	}

}
