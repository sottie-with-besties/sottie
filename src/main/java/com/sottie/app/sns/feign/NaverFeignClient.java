package com.sottie.app.sns.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sottie.app.sns.dto.NaverProfile;
import com.sottie.app.sns.dto.ResponseNaver;

@FeignClient(name = "naverFeignClient", url = "https://openapi.naver.com/v1/nid")
public interface NaverFeignClient {

	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseNaver<NaverProfile> getProfile(@RequestHeader("Authorization") String token);

}
