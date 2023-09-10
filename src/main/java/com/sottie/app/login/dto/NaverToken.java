package com.sottie.app.login.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NaverToken {
	private String token;
	private String expire_date;
	private String allowed_profile;
}
