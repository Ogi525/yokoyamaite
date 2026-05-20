package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {

	// おすすめ商品
	List<Product> findRecommendProducts();

	// 新着商品
	List<Product> findNewProducts();

	// ランキング商品
	List<Product> findRankingProducts();

	// ★ 商品詳細
	Product findById(@Param("id") Integer id);

	// 在庫更新
	void updateStock(Product product);

	//データから呼び出す
	Product findByProductName(String productName);
}
