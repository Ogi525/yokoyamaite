package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Post;

@Mapper
public interface PostMapper {

	void insertPost(Post post);

	List<Post> findParentPostsByProduct(Integer productId);

	List<Post> findReplies(Integer parentId);

	Post findById(Integer id);

	void deleteById(Integer id);

	void deleteReplies(Integer paretId);
}