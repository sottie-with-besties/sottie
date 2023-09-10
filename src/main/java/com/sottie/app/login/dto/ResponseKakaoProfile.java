package com.sottie.app.login.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseKakaoProfile {
	private Long id;
	private boolean has_signed_up;
	// private LocalDateTime connected_at;
	// private LocalDateTime synched_at;
	// private ?? properties;
	private KakaoAccount kakao_account;
	private KakaoPartner for_partner;
}
