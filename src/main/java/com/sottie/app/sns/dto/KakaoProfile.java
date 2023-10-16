package com.sottie.app.sns.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoProfile {
	private String nickname;
	private String thumbnail_image_url;
	private String profile_image_url;
	private boolean is_default_image;
}
