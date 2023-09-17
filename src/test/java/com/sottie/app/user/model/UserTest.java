package com.sottie.app.user.model;

public class UserTest {

	public static User user() {
		return User.builder()
			.name("name")
			.nickName("nickName")
			.gender(Gender.FEMALE)
			.email("test@gmail.com")
			.password("test123!!")
			.build();
	}
}
