package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void register(UserForm form) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setPostalCode(form.getPostalCode());
		user.setAddress(form.getAddress());
		userMapper.insert(user);

	}

	@Override
	public User findById(int id) {

		return userMapper.findById(id);
	}

	@Override
	public User login(String email) {

		return userMapper.login(email);
	}
}
