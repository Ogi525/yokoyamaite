package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;
import com.example.demo.mapper.CartMapper;
import com.example.demo.mapper.OrderMapper;

@Service
public class OrderService {

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private OrderMapper orderMapper;

	// 在庫チェック
	public boolean hasStock(Integer productId, Integer quantity) {
		Integer stock = orderMapper.findStockByProductId(productId);

		return stock != null && stock >= quantity;
	}

	// 在庫を減らす
	public void decreaseStock(Integer productId, Integer quantity) {
		orderMapper.decreaseStock(productId, quantity);
	}

	// 注文情報保存
	public void saveOrder(
			Integer userId,
			List<CartItem> cart,
			Integer grandTotal,
			Date orderDate) {

		cartMapper.insertOrder(userId, grandTotal);

		Integer orderId = cartMapper.getLastOrderId();

		for (CartItem item : cart) {
			orderMapper.insertOrderDetail(
					orderId,
					item.getProductId(),
					item.getQuantity(),
					item.getPrice(),
					orderDate);
		}
	}

	// クーポン使用済みに変更
	public void useCoupon(Integer couponId, Integer userId) {

		if (couponId == null) {
			return;
		}

		orderMapper.useCoupon(couponId, userId);
	}
}