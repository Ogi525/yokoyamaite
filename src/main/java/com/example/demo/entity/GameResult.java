package com.example.demo.entity;

import java.time.LocalDate;

public class GameResult {
	private Integer id;
	private Integer userId;
	private String result;
	private LocalDate playedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDate getPlayeDate() {
		return playedDate;
	}

	public void setPlayeDate(LocalDate playedDate) {
		this.playedDate = playedDate;
	}
}
