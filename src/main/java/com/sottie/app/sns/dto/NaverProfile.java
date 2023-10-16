package com.sottie.app.sns.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NaverProfile {
	private String id;
	private String nickname;
	private String name;
	private String email;
	private String gender;
	private String age;
	private String birthday;
	private String profile_image;
	private String birthyear;
	private String mobile;
}
