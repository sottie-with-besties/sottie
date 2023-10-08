package com.sottie.app.sms.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sottie.app.sms.service.SmsService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sms")
public class SmsRestController {

	private final SmsService smsService;

	@Operation(summary = "인증 코드 문자 발송", description = "인증 코드 문자 발송")
	@GetMapping(value = "sendVerifyCode")
	public String sendVerifyCode(@RequestParam String phoneNumber) {
		return smsService.sendVerifyCode(phoneNumber);
	}
}



