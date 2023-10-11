package com.sottie.app.user.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class EncryptorTest {
	@Test
	void 비밀번호_암호화() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// given
		String rawPassword = "12345678";

		// when
		String encodedPassword = encoder.encode(rawPassword);

		// then
		assertAll(
			() -> assertNotEquals(rawPassword, encodedPassword),
			() -> assertTrue(encoder.matches(rawPassword, encodedPassword))
		);
	}

}
