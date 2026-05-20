package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.History;
import com.example.demo.mapper.HistoryMapper;

@Service
public class HistoryService {
	private final HistoryMapper historyMapper;

	public HistoryService(HistoryMapper historyMapper) {
		this.historyMapper = historyMapper;
	}

	public List<History> findByUserId(Integer userId) {
		return historyMapper.findByUserId(userId);
	}

}