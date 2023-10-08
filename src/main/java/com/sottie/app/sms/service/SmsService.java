package com.sottie.app.sms.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsService {

	// 문자 전송
	public boolean send(String phoneNumber, String message) {
		// 문자 전송
		return true;
	}

	// 인증 코드 문자 발송
	public String sendVerifyCode(String phoneNumber) {
		String verifyCode = getVerifyCode();
		String message = "인증번호는 [" + verifyCode + "] 입니다.";
		boolean sendResult = send(phoneNumber, message);

		return sendResult ? verifyCode : null;
	}

	// 6자리 인증 코드 생성
	private String getVerifyCode() {
		return String.valueOf(RandomUtils.nextInt(100000, 999999));
	}

}
