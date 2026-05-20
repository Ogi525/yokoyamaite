package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Coupon;

@Mapper
public interface CouponMapper {

	@Select("""
				SELECT
					c.id,
					c.code,
					c.name,
					c.discount_value AS discountValue,
					uc.is_used AS isUsed,
					c.end_at AS endAt

				FROM users_coupons uc

				INNER JOIN coupons c
					ON uc.coupon_id = c.id

				WHERE uc.user_id = #{userId}
				  AND uc.is_used = false
			""")
	List<Coupon> findByUserId(Integer userId);

	@Select("""
				SELECT
					c.id,
					c.code,
					c.name,
					c.discount_value AS discountValue,
					uc.is_used AS isUsed,
					c.end_at AS endAt

				FROM users_coupons uc

				INNER JOIN coupons c
					ON uc.coupon_id = c.id

				WHERE c.id = #{couponId}
				  AND uc.user_id = #{userId}
				  AND uc.is_used = false
			""")
	Coupon findCouponByIdAndUserId(
			Integer couponId,
			Integer userId);
}