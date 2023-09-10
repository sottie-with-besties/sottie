package com.sottie.app.login.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.sottie.app.login.dto.NaverProfile;
import com.sottie.app.login.dto.NaverToken;
import com.sottie.app.login.dto.ResponseNaver;

@FeignClient(name = "naverFeignClient", url = "https://openapi.naver.com/v1/nid")
public interface NaverFeignClient {

	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseNaver<NaverProfile> getProfile(@RequestHeader("Authorization") String token);

	@GetMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseNaver<NaverToken> verifyToken(@RequestHeader("Authorization") String token, @RequestParam boolean info);
}
