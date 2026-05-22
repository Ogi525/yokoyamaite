package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Comment;

public interface CommentService {

	List<Comment> findByProductId(Integer productId);

}