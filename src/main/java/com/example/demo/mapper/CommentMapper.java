package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Comment;

@Mapper
public interface CommentMapper {

	List<Comment> findByProductId(@Param("productId") Integer productId);
}