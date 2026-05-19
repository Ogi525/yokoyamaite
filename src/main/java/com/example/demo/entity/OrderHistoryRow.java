package com.example.demo.entity;

public class OrderHistoryRow {
	private int id;
	private String imageurl;
	private int price;
	private int productId;

	int getUsers() {
		return id;
	}

	void setUsers(int id) {
		this.id = id;
	}

	String getImageUrl() {
		return imageurl;
	}

	void setImageUrl(String imageurl) {
		this.imageurl = imageurl;
	}

	int getPrice() {
		return price;
	}

	void setPrice(int price) {
		this.price = price;
	}

	int getProductId() {
		return productId;
	}

	void setProductId(int productId) {
		this.productId = productId;
	}

}