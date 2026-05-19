package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Review;
import com.example.demo.mapper.GameMapper;
import com.example.demo.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper reviewMapper;
	private final GameMapper gameMapper;

	public ReviewServiceImpl(ReviewMapper reviewMapper, GameMapper gameMapper) {
		this.reviewMapper = reviewMapper;
		this.gameMapper = gameMapper;
	}

	@Override
	public void saveReview(Review review) {
		reviewMapper.insertReview(review);
		//100文字以上でクーポン付与
		if (review.getComment().length() >= 100) {
			Integer couponId = gameMapper.getCouponIdByDiscount(500);
			gameMapper.insertUserCoupon(review.getUserId(), couponId);

			gameMapper.insertUserCoupon(
					review.getUserId(),
					couponId);
		}
	}

	@Override
	public List<Review> getReviews(Integer productId) {
		return reviewMapper.findByProductId(productId);
	}

	@Override
	public boolean hasPurchased(Integer userId, Integer productId) {
		return reviewMapper.hasPurchased(userId, productId) > 0;
	}

	@Override
	public boolean hasReviewed(Integer userId, Integer productId) {
		return reviewMapper
				.findByUserIdAndProductId(userId, productId) != null;
	}
}