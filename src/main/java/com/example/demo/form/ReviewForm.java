package com.example.demo.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ReviewForm {

	@Min(1)
	@Max(5)
	private Integer rating;

	@NotBlank
	@Size(max = 200)
	@Pattern(regexp = "^(?!.*(あああ|aaa|AAA|test|Test|テスト|てすと|適当|なし|特になし|asdf|qwerty)).*[ぁ-んァ-ン一-龥a-zA-Z].*$", message = "レビュー内容が不適切です。商品の感想を具体的に入力してください")
	private String comment;

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}