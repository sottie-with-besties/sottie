package com.sottie.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface Encryptor {

	default String encrypt(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	default boolean isMatched(String plain, String encrypted) {
		return new BCryptPasswordEncoder().matches(plain, encrypted);
	}
}
