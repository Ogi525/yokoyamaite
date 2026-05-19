package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Coupon;

@Mapper
public interface CartMapper {

	// ユーザーの未使用クーポン一覧取得
	List<Coupon> findUserCoupons(@Param("userId") Integer userId);

	// 選択されたクーポン取得
	Coupon findCouponByIdAndUserId(
			@Param("couponId") Integer couponId,
			@Param("userId") Integer userId);

	Integer findStockByProductId(Integer productId);

	void decreaseStock(
			@Param("productId") Integer productId,
			@Param("quantity") Integer quantity);

	void insertOrder(
			@Param("userId") Integer userId,
			@Param("grandTotal") Integer grandTotal);

	Integer getLastOrderId();

}
