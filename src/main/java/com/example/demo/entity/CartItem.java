package com.example.demo.entity;

public class CartItem {
	private Integer userId;

	private int productId;

	private String productName;
	private int price;
	private int stock;
	private int quantity;
	private Boolean hidden;

	public CartItem(int productId, String productName, int price, int stock) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.quantity = 1;

	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getStock() {
		return stock;
	}

	public void incrementQuantity() {
		this.quantity++;
	}

	public void decrementQuantity() {
		this.quantity--;
	}

	public int getSubtotal() {
		return price * quantity;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
}