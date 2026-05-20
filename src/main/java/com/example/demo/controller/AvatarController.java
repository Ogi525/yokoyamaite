package com.example.demo.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvatarController {

	@GetMapping(value = "/avatar/{name}", produces = "image/svg+xml")
	@ResponseBody
	public String avatar(@PathVariable String name) {

		Random random = new Random(name.hashCode());

		String initial = name.substring(0, 1);

		int colorCodeR = random.nextInt(256);
		int colorCodeG = random.nextInt(256);
		int colorCodeB = random.nextInt(256);

		String colorFill = "rgb(" + colorCodeR + "," + colorCodeG + "," + colorCodeB + ")";

		String imageSrc = """
							<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">

				    <circle cx="50" cy="50" r="50" fill="%s" />

				    <text x="50%%"
				          y="50%%"
				          dy=".35em"
				          font-size="40"
				          font-weight="bold"
				          text-anchor="middle"
				          fill="white"
				          font-family="Arial, sans-serif">
				        %s
				    </text>

				</svg>
						""";

		return imageSrc.formatted(colorFill, initial);
	}
}