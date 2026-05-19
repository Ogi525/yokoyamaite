package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Review;
import com.example.demo.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper reviewMapper;

	public ReviewServiceImpl(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}

	@Override
	public void saveReview(Review review) {
		reviewMapper.insertReview(review);
	}

	@Override
	public List<Review> findByProductId(Integer productId) {
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