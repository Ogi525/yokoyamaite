package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {

	// おすすめ商品一覧取得
	List<Product> findRecommendProducts();

	// 新着商品一覧取得
	List<Product> findNewProducts();

	// ランキング商品一覧取得
	List<Product> findRankingProducts();
}
