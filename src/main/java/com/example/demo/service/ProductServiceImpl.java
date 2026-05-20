package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	/**
	 * おすすめ商品一覧取得
	 */
	@Override
	public List<Product> findRecommendProducts() {

		return productMapper.findRecommendProducts();
	}

	/**
	 * 新着商品一覧取得
	 */
	@Override
	public List<Product> findNewProducts() {

		return productMapper.findNewProducts();
	}

	/**
	 * ランキング商品一覧取得
	 */
	@Override
	public List<Product> findRankingProducts() {

		return productMapper.findRankingProducts();
	}

	/**
	 * 商品詳細取得
	 */
	@Override
	public Product findById(Integer id) {

		return productMapper.findById(id);
	}

	/**
	 * ゴールデンりんちゃん取得
	 */
	@Override
	public Product getGoldenRinchan() {

		return productMapper.findByProductName("ゴールデンりんちゃん");
	}
}