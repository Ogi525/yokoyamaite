package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Coupon;

@Mapper
public interface CartMapper {

	// ユーザーが持っている未使用クーポン一覧を取得
	List<Coupon> findUserCoupons(
			@Param("userId") Integer userId);

	// 指定されたクーポンが
	// ログインユーザーのものか確認して取得
	Coupon findCouponByIdAndUserId(
			@Param("couponId") Integer couponId,
			@Param("userId") Integer userId);

	// 注文情報をordersテーブルに登録
	void insertOrder(
			@Param("userId") Integer userId,
			@Param("grandTotal") Integer grandTotal);

	// 直前に登録した注文IDを取得
	Integer getLastOrderId();

}