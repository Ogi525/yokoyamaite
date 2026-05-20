package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Review;

@Mapper
public interface ReviewMapper {

	// レビュー投稿
	@Insert("""
			    INSERT INTO reviews (
			        user_id,
			        product_id,
			        rating,
			        comment
			    )
			    VALUES (
			        #{userId},
			        #{productId},
			        #{rating},
			        #{comment}
			    )
			""")
	void insertReview(Review review);

	// 商品レビュー一覧
	@Select("""
			    SELECT *
			    FROM reviews
			    WHERE product_id = #{productId}
			    ORDER BY created_at DESC
			""")
	List<Review> findByProductId(Integer productId);

	// 購入済み判定
	@Select("""
			    SELECT COUNT(*)
			    FROM orders_products op
			    JOIN orders o ON op.order_id = o.id
			    WHERE o.user_id = #{userId}
			    AND op.product_id = #{productId}
			""")
	Integer hasPurchased(
			@Param("userId") Integer userId,
			@Param("productId") Integer productId);

	// 重複レビュー判定
	@Select("""
			    SELECT *
			    FROM reviews
			    WHERE user_id = #{userId}
			    AND product_id = #{productId}
			""")
	Review findByUserIdAndProductId(
			@Param("userId") Integer userId,
			@Param("productId") Integer productId);
}