package com.sottie.app.sns.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.sns.dto.NaverProfile;
import com.sottie.app.sns.dto.ResponseNaver;
import com.sottie.app.sns.feign.NaverFeignClient;
import com.sottie.app.sns.application.NaverService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/naver")
public class NaverRestController {

	private final NaverService naverService;

	private final NaverFeignClient naverFeignClient;

	@Operation(summary = "네이버 아이디로 로그인 체크", description = "네이버 아이디로 로그인 체크. 회원가입 및 로그인 진행")
	@PostMapping(value = "loginProcess")
	public ResponseEntity<String> loginProcess(@RequestBody NaverProfile naverProfile) {
		naverService.loginProcess(naverProfile);
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}

	@GetMapping(value = "getProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseNaver<NaverProfile> getProfile(@RequestParam String token) {
		return naverFeignClient.getProfile("Bearer " + token);
	}

}
