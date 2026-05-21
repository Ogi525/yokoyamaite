package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component("videoUrlConverter")
public class VideoUrlConverter {

	public String toEmbedUrl(String url) {
		if (url == null || url.isEmpty())
			return "";

		// 短縮URL: https://youtu.be/VIDEO_ID
		if (url.contains("youtu.be/")) {
			String videoId = url.split("youtu.be/")[1].split("\\?")[0];
			return "https://www.youtube.com/embed/" + videoId;
		}

		// 通常URL: https://www.youtube.com/watch?v=VIDEO_ID
		if (url.contains("watch?v=")) {
			String videoId = url.split("watch\\?v=")[1].split("&")[0];
			return "https://www.youtube.com/embed/" + videoId;
		}

		return url;
	}
}
