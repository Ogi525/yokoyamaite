package com.example.demo.search;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;

@Service
public class SearchService {

	private final SearchMapper searchMapper;

	public SearchService(SearchMapper searchMapper) {
		this.searchMapper = searchMapper;
	}

	public List<Product> search(
			String keyword,
			List<Integer> areas,
			List<Integer> categories,
			Integer minPrice,
			Integer maxPrice,
			String sort) {

		return searchMapper.search(
				keyword,
				areas,
				categories,
				minPrice,
				maxPrice,
				sort);
	}
}