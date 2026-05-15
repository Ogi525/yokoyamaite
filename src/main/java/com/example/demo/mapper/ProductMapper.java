package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {

	// おすすめ商品
	List<Product> findRecommendProducts();

	// 新着商品
	List<Product> findNewProducts();

	// ランキング商品
	List<Product> findRankingProducts();
}
