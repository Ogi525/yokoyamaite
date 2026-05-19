package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.User;

@Mapper
public interface ProfileMapper {

	@Update("""
				UPDATE users
				SET name = #{name},
				    email = #{email},
				    address = #{address}
				WHERE id = #{id}
			""")
	void updateUser(User user);
}