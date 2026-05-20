package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Coupon;

@Mapper
public interface CouponMapper {
	@Select("""
				SELECT c.id, c.code,c.name,c.discount_value AS discount
				FROM users_coupons uc
				JOIN coupons c
				  ON uc.coupon_id = c.id
				WHERE uc.user_id = #{userId}
			""")
	List<Coupon> findByUserId(Integer userId);
}
