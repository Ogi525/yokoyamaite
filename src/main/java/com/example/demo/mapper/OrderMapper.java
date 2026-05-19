package com.example.demo.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.History;

@Mapper
public interface OrderMapper {

	//注文履歴に残す
	void insertOrderDetail(
			@Param("orderId") Integer orderId,
			@Param("productId") Integer productId,
			@Param("quantity") Integer quantity,
			@Param("price") Integer price,
			@Param("date") Date orderdate);

	//クーポンを使用済みにする
	void useCoupon(
			@Param("couponId") Integer couponId,
			@Param("userId") Integer userId);

	// 注文履歴一覧取得
	List<History> findByUserId(Integer userId);

}