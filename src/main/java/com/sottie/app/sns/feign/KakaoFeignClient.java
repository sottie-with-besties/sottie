package com.sottie.app.sns.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sottie.app.sns.dto.ResponseKakaoProfile;

@FeignClient(name = "kakaoFeignClient", url = "https://kapi.kakao.com/v2/user")
public interface KakaoFeignClient {

	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
		MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8")
	ResponseKakaoProfile getProfile(@RequestHeader("Authorization") String token);

}
