
package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.mapper.CartMapper;

@Service
public class CheckoutService {

	@Autowired
	private CartMapper cartMapper;

	// 決済画面に表示する「使えるクーポン一覧」
	public List<Coupon> getAvailableCoupons(Integer userId, Integer totalPrice) {

		List<Coupon> coupons = cartMapper.findUserCoupons(userId);

		return coupons.stream()
				.filter(coupon -> coupon.getDiscountValue() <= totalPrice)
				.toList();
	}

	// 選ばれたクーポンの割引額を取得
	public Integer getDiscountAmount(Integer couponId, Integer userId, Integer totalPrice) {

		// クーポン未選択なら0円引き
		if (couponId == null) {
			return 0;
		}

		Coupon coupon = cartMapper.findCouponByIdAndUserId(couponId, userId);

		// クーポンが存在しない場合
		if (coupon == null) {
			return 0;
		}

		// 商品合計より値引額が大きい場合は使わせない
		if (coupon.getDiscountValue() > totalPrice) {
			return 0;
		}

		return coupon.getDiscountValue();
	}

	// 割引後の金額を計算
	public Integer calculateFinalPrice(Integer totalPrice, Integer discountAmount) {
		return totalPrice - discountAmount;
	}

	// 送料を計算する
	public Integer calculateShippingFee(Integer totalPrice, Integer discountAmount) {

		// 3000円以上送料無料
		if (totalPrice - discountAmount >= 3000) {
			return 0;
		}

		return 500;
	}

	// すべての合計を計算する
	public Integer calculateGrandTotal(
			Integer totalPrice,
			Integer discountAmount,
			Integer shippingFee) {

		return totalPrice - discountAmount + shippingFee;
	}

	public List<Coupon> getAvailableCoupons(Integer userId) {

		return cartMapper.findUserCoupons(userId);
	}

}