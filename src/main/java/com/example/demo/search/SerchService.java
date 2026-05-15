package com.example.demo.search;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SerchService {

	private final SerchMapper itemMapper;

	public SerchService(SerchMapper itemMapper) {
		this.itemMapper = itemMapper;
	}

	public List<Item> search(
			String keyword,
			String area,
			String category,
			Integer minPrice,
			Integer maxPrice,
			String sort) {

		return itemMapper.search(
				keyword,
				area,
				category,
				minPrice,
				maxPrice,
				sort);
	}
}