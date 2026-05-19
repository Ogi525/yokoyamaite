package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class PostForm {
	private Integer productId;
	@NotBlank(message = "コメントを入力してください")
	@Size(max = 500, message = "500文字以内で入力してください")
	private String body;
	private Integer parentId;
}
