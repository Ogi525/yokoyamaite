package com.example.demo.service;

import com.example.demo.UserForm;

public interface UserService {

	/**
	 * ユーザを登録する。
	 * @param form 登録フォームの入力値
	 */
	void register(UserForm form);

	boolean existsByEmail(String email);
}
