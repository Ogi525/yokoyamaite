package com.example.demo.search;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SerchMapper {

	List<Item> search(

			@Param("keyword") String keyword,

			@Param("area") String area,

			@Param("category") String category,

			@Param("minPrice") Integer minPrice,

			@Param("maxPrice") Integer maxPrice,

			@Param("sort") String sort);
}