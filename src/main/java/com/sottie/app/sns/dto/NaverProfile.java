package com.sottie.app.sns.dto;

import com.sottie.app.user.model.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NaverProfile {
	@Schema(name = "이용자 식별자", description = "64자 이내로 구성된 BASE64 형식의 문자열", example = " abcdefgABCDEFG1234567")
	private String id;

	@Schema(name = "이름", description = "10자 이내로 구성된 문자열", example = "네이버")
	private String name;

	@Schema(name = "닉네임", description = "20자 이내로 구성된 문자열", example = "네이버닉네임")
	private String nickname;

	@Schema(name = "프로필 이미지", description = "255자 이내로 구성된 URL 형태의 문자열", example = "https://phinf.pstatic.net/.../image.jpg")
	private String profile_image;

	@Schema(name = "연락처 이메일 주소", description = "이메일 규격의 문자열", example = "naveridlogin@naver.com")
	private String email;

	@Schema(name = "생일", description = "월-일 (MM-DD) 형태의 문자열", example = "08-15")
	private String birthday;

	@Schema(name = "연령대", description = "연령 구간에 따라 0-9 / 10-19 / 20-29 / 30-39 / 40-49 / 50-59 / 60- 으로 표현된 문자열", example = "20-29")
	private String age;

	@Schema(name = "성별", description = "M/F (남성/여성) 으로 표된 문자", example = "F")
	private String gender;

	@Schema(name = "출생연도", description = "연(YYYY) 형태의 문자열", example = "1900")
	private String birthyear;

	@Schema(name = "휴대전화번호", description = "대쉬(-)를 포함한 휴대전화번호 문자열", example = "010-0000-0000")
	private String mobile;

	public Gender getGender() {
		if ("M".equals(gender)) {
			return Gender.MALE;
		} else if ("F".equals(gender)) {
			return Gender.FEMALE;
		}
		return null;
	}
}
