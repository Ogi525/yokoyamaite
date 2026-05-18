package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;

public interface UserService {

	/**
	 * ユーザを登録する。
	 * @param form 登録フォームの入力値
	 */
	void register(UserForm form);

	/**
	 * IDからユーザー情報を取得する
	 * @param id ユーザーID
	 * @return ユーザー情報
	 */
	User findByEmail(String email);

	User login(String email);

}
