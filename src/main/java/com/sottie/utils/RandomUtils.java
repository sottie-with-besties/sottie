package com.sottie.utils;

public class RandomUtils {

	// 6자리 인증 코드 생성
	public static String getVerifyCode() {
		return String.valueOf(org.apache.commons.lang3.RandomUtils.nextInt(100000, 999999));
	}
}
