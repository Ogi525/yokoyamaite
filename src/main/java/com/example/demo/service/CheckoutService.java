package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.mapper.CouponMapper;

@Service
public class CheckoutService {

	@Autowired
	private CouponMapper couponMapper;

	// 決済画面に表示する「使えるクーポン一覧」
	public List<Coupon> getAvailableCoupons(
			Integer userId,
			Integer totalPrice) {

		List<Coupon> coupons = couponMapper.findByUserId(userId);

		return coupons.stream()

				// 商品合計より値引き額が大きいものは除外
				.filter(coupon -> coupon.getDiscountValue() <= totalPrice)

				// 使用済み除外
				.filter(coupon -> !coupon.isUsed())

				// 期限切れ除外
				.filter(coupon -> coupon.getEndAt() == null
						|| coupon.getEndAt().isAfter(LocalDateTime.now()))

				.toList();
	}

	// 選ばれたクーポンの割引額を取得
	public Integer getDiscountAmount(
			Integer couponId,
			Integer userId,
			Integer totalPrice) {

		// クーポン未選択なら0円引き
		if (couponId == null) {
			return 0;
		}

		Coupon coupon = couponMapper.findCouponByIdAndUserId(
				couponId,
				userId);

		// クーポンが存在しない場合
		if (coupon == null) {
			return 0;
		}

		// 使用済みなら無効
		if (coupon.isUsed()) {
			return 0;
		}

		// 期限切れなら無効
		if (coupon.getEndAt() != null
				&& coupon.getEndAt().isBefore(LocalDateTime.now())) {

			return 0;
		}

		// 商品合計より値引額が大きい場合は使わせない
		if (coupon.getDiscountValue() > totalPrice) {
			return 0;
		}

		return coupon.getDiscountValue();
	}

	// 割引後金額
	public Integer calculateFinalPrice(
			Integer totalPrice,
			Integer discountAmount) {

		return totalPrice - discountAmount;
	}

	// 送料計算
	public Integer calculateShippingFee(
			Integer totalPrice,
			Integer discountAmount) {

		// 3000円以上送料無料
		if (totalPrice - discountAmount >= 3000) {
			return 0;
		}

		return 500;
	}

	// 最終合計
	public Integer calculateGrandTotal(
			Integer totalPrice,
			Integer discountAmount,
			Integer shippingFee) {

		return totalPrice - discountAmount + shippingFee;
	}
}