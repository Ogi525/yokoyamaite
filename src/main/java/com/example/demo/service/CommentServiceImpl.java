package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<Comment> findByProductId(Integer productId) {

		return commentMapper.findByProductId(productId);
	}
}