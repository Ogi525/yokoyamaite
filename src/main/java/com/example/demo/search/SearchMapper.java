package com.example.demo.search;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Product;

@Mapper
public interface SearchMapper {

	List<Product> search(
			@Param("keyword") String keyword,
			@Param("areas") List<Integer> areas,
			@Param("categories") List<Integer> categories,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice,
			@Param("sort") String sort);
}