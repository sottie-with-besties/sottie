package com.sottie.app.login.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.login.dto.ResponseKakaoProfile;
import com.sottie.app.login.feign.KakaoFeignClient;

@RestController
@RequestMapping(value = "/kakao")
public class KakaoLoginController {

	@Autowired
	private KakaoFeignClient kakaoFeignClient;

	@GetMapping(value = "getProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseKakaoProfile getProfile(@RequestParam String token) {
		return kakaoFeignClient.getProfile("Bearer " + token);
	}
}
