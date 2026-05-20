package com.example.demo.service;

import java.util.List;

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
}
