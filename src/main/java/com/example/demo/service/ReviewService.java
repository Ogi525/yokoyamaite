package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Review;

public interface ReviewService {

	void saveReview(Review review);

	List<Review> findByProductId(Integer productId);

	boolean hasPurchased(Integer userId, Integer productId);

	boolean hasReviewed(Integer userId, Integer productId);
}