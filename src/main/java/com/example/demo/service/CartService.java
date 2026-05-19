package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class CartService {

	private static final String CART_KEY = "cart";

	@Autowired
	private ProductMapper productMapper;

	/** セッションからカートを取得する（存在しなければ空のリストを返す） */
	@SuppressWarnings("unchecked")
	public List<CartItem> getCart(HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_KEY);
		if (cart == null) {
			cart = new ArrayList<>();
			session.setAttribute(CART_KEY, cart);
		}
		return cart;
	}

	/** カートに商品を追加する（同じ商品が既にあれば数量を増やす） */
	public void addItem(HttpSession session, Product product) {
		List<CartItem> cart = getCart(session);
		for (CartItem item : cart) {
			if (item.getProductId() == product.getId()) {
				item.incrementQuantity();
				return;
			}
		}
		cart.add(new CartItem(product.getId(), product.getName(), product.getPrice(), product.getStock()));
	}

	/** カートから商品を削除する */
	public void removeItem(HttpSession session, int productId) {
		List<CartItem> cart = getCart(session);
		cart.removeIf(item -> item.getProductId() == productId);
	}

	/**在庫以上にカートの商品を増やせないようにする
	   カートの商品を増やす*/
	public boolean incrementQuantity(
			HttpSession session,
			int productId) {

		List<CartItem> cart = getCart(session);

		// DBから最新商品取得
		Product product = productMapper.findById(productId);

		for (CartItem item : cart) {

			if (item.getProductId() == productId) {

				// 在庫以上なら増やさない
				if (item.getQuantity() >= product.getStock()) {

					return false;
				}

				item.incrementQuantity();

				return true;
			}
		}

		return false;
	}

	/**カートの商品を減らす*/
	public void decrementQuantity(HttpSession session, int productId) {
		List<CartItem> cart = getCart(session);
		for (CartItem item : cart) {
			if (item.getProductId() == productId) {
				item.decrementQuantity();
				break;
			}

		}

		cart.removeIf(cartItem -> cartItem.getQuantity() <= 0);

	}

	/** カートを空にする */
	public void clearCart(HttpSession session) {
		session.removeAttribute(CART_KEY);
	}

	/**カートがカラかどうかを判定する*/
	public boolean isCartEmpty(HttpSession session) {
		List<CartItem> cart = getCart(session);
		return cart == null || cart.isEmpty();
	}

}
