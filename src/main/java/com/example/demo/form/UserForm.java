package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;

public class UserForm {
	@NotBlank(message = "名前は必須です。")
	private String name;
	@NotBlank(message = "emailアドレスは必須です")
	private String email;
	@NotBlank(message = "passwordを入力してください。")
	private String password;
	private String postalcode;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPostalCode() {
		return postalcode;
	}

	public void setPostalCode(String postalCode) {
		this.postalcode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}