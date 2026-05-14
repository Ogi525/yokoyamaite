package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	// ユーザー登録
	void insert(User user);

	// ログイン
	User login(@Param("email") String email,
			@Param("password") String password);

}