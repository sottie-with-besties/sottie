package com.sottie.app.sms.application;

import org.springframework.stereotype.Service;

import com.sottie.utils.RandomUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnsService {

	// 문자 전송
	public boolean send(String phoneNumber, String message) {
		// 문자 전송
		return true;
	}

	// 인증 코드 문자 발송
	public String sendVerifyCode(String phoneNumber) {
		String verifyCode = RandomUtils.getVerifyCode();
		String message = "인증번호는 [" + verifyCode + "] 입니다.";
		boolean sendResult = send(phoneNumber, message);

		return sendResult ? verifyCode : null;
	}



}
