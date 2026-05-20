package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.GameMapper;

@Service
public class GameService {
	private final GameMapper gameMapper;

	public GameService(GameMapper gameMapper) {
		this.gameMapper = gameMapper;
	}

	public boolean isPlayedToday(Integer userId) {
		return gameMapper.countTodayPlay(userId) > 0;
	}

	public String getTodayCouponCode(Integer userId) {
		return gameMapper.getTodayCouponCode(userId);
	}

	@Transactional
	public Map<String, Object> playGame(Integer userId) {

		Map<String, Object> resultMap = new HashMap<>();

		//　今日プレイ済みか確認する
		if (isPlayedToday(userId)) {
			resultMap.put("result", "ALREADY");
			resultMap.put("couponCode", getTodayCouponCode(userId));
			return resultMap;
		}

		int rand = new Random().nextInt(100);

		String result;

		// 当たり判定
		if (rand < 80) {
			result = "BIGWIN";//2%
		} else if (rand < 100) {
			result = "WIN";//8%
		} else {
			result = "LOSE";//90%
		}

		//結果保存
		gameMapper.insertGameResult(userId, result);
		String couponCode = null;

		//当たりの場合クーポンを保存
		if ("BIGWIN".equals(result)) {
			Integer couponId = gameMapper.getCouponIdByDiscount(1000);
			gameMapper.insertUserCoupon(userId, couponId);

			couponCode = gameMapper.getTodayCouponCode(userId);
		} else if ("WIN".equals(result)) {
			Integer couponId = gameMapper.getCouponIdByDiscount(500);
			gameMapper.insertUserCoupon(userId, couponId);

			couponCode = gameMapper.getTodayCouponCode(userId);
		}

		resultMap.put("result", result);
		resultMap.put("couponCode", couponCode);

		return resultMap;

	}

}
