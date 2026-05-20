package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.service.GameService;

@Controller
@RequestMapping("/bonusgame")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	//ゲーム画面を表示する
	@GetMapping
	public String gamePage(HttpSession session, Model model) {

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		Integer userId = loginUser.getId();

		boolean playedToday = gameService.isPlayedToday(userId);
		String couponCode = null;

		if (playedToday) {
			couponCode = gameService.getTodayCouponCode(userId);
		}

		model.addAttribute("playedToday", playedToday);
		model.addAttribute("couponCode", couponCode);

		return "game/game";
	}

	@PostMapping("/spin")
	@ResponseBody
	public Map<String, Object> playGame(HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "notLoggedIn");
			return error;
		}

		Integer userId = loginUser.getId();
		Map<String, Object> resultMap = gameService.playGame(userId);
		String result = (String) resultMap.get("result");
		String couponCode = (String) resultMap.get("couponCode");

		Map<String, Object> response = new HashMap<>();

		if ("BIGWIN".equals(result)) {
			response.put("title", "当たり！");
			response.put("message", "クーポンGET!" + couponCode);
			response.put("results", List.of("🎁", "🎁", "🎁"));

		} else if ("WIN".equals(result)) {
			response.put("title", "当たり！");
			response.put("message", "クーポンGET!" + couponCode);
			response.put("results", List.of("🍒", "🍒", "🍒"));
		} else if ("LOSE".equals(result)) {
			response.put("title", "はずれ");
			response.put("message", "また明日挑戦してね！");
			response.put("results", List.of("🍒", "🍇", "🍀"));
		} else if ("ALREADY".equals(result)) {
			response.put("title", "プレイ済み");

			if (couponCode != null) {
				response.put("message", "今日ののクーポンコード: " + couponCode);
			} else {
				response.put("message", "また明日");
			}

			response.put("results", List.of("❌", "❌", "❌"));
		}
		return response;

	}
}