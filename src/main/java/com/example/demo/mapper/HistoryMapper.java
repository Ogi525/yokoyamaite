package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.History;

@Mapper
public interface HistoryMapper {

	// 購入履歴一覧取得
	List<History> findByUserId(Integer userId);

}