package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.mapper.CouponMapper;

@Service
public class CouponService {

	private final CouponMapper couponMapper;

	public CouponService(CouponMapper couponMapper) {
		this.couponMapper = couponMapper;
	}

	public List<Coupon> findByUserId(Integer userId) {
		return couponMapper.findByUserId(userId);
	}

	// 追加：レビュー100文字以上特典クーポン発行
	public void issueReviewCoupon(Integer userId) {

		Coupon coupon = new Coupon();

		coupon.setCode("REVIEW-" + UUID.randomUUID().toString().substring(0, 8));
		coupon.setName("レビュー投稿特典500円クーポン");
		coupon.setDiscountValue(500);
		coupon.setEndAt(LocalDateTime.now().plusMonths(1));

		// couponsテーブルに登録
		couponMapper.insertCoupon(coupon);

		// users_couponsテーブルに紐づけ
		couponMapper.insertUserCoupon(userId, coupon.getId());
	}
}