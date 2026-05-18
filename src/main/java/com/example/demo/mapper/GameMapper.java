package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GameMapper {

	//今日プレイ済みか確認
	Integer countTodayPlay(@Param("userId") Integer userId);

	void insertGameResult(
			@Param("userId") Integer userId,
			@Param("result") String result);

	//クーポン取得
	Integer getCouponIdByDiscount(@Param("discount") int discount);

	// クーポン付与
	void insertUserCoupon(
			@Param("userId") Integer userId,
			@Param("couponId") Integer couponId);

	String getTodayCouponCode(@Param("userId") Integer userId);

}
