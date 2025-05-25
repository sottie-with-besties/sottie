package com.sottie.authentication;

import com.sottie.processor.ProcessInfoUtils;
import com.sottie.processor.SottieAuthenticationProcessor;
import com.sottie.properties.SottieProperties;
import com.sottie.utils.SottieWebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Sottie 인증을 관리하는 클래스
 * Spring Security의 AuthenticationManager를 구현하여 인증 프로세스를 처리
 */
@Slf4j
@RequiredArgsConstructor
public class SottieAuthenticationManager implements AuthenticationManager {

    private final SottieProperties sottieProperties;
    private final SottieAuthenticationProcessor<SottieAuthenticationRequestToken, SottieAuthentication> sottieAuthenticationProcessor;
    private final JwtProvider tokenProvider;

    private static final String ACCESS_TOKEN = "acc-token";
    private static final String REFRESH_TOKEN = "ref-token"; // 현재 사용되지 않는 상수

    /**
     * 표준 Spring Security Authentication 객체를 처리하는 메서드
     * @param authentication 인증 객체
     * @return SottieAuthentication 인증 완료된 객체
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Processing authentication: {}", authentication);
        
        // 인증 객체 생성
        SottieAuthentication sottieAuthentication = SottieAuthentication.builder()
                .processId(ProcessInfoUtils.getCurrentProcessId())
                .name(authentication.getName())
                .roles(List.of(() -> "ROLE_USER"))
                .build();
        
        // 응답 헤더에 액세스 토큰 설정
        SottieWebUtils.getResponse().setHeader(ACCESS_TOKEN, tokenProvider.generate(sottieAuthentication));
        
        // 보안 컨텍스트에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(sottieAuthentication);
        
        return sottieAuthentication;
    }

    /**
     * Sottie 전용 인증 요청 토큰을 처리하는 메서드
     * @param authenticationRequestToken Sottie 인증 요청 토큰
     * @return 인증된 Authentication 객체, 실패 시 null
     */
    public Authentication authenticate(SottieAuthenticationRequestToken authenticationRequestToken) throws AuthenticationException {
        try {
            Authentication authentication = sottieAuthenticationProcessor.authenticate(authenticationRequestToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            return null; // 예외 발생 시 null 반환 대신 예외를 던지는 것이 더 좋은 패턴일 수 있음
        }
    }

    /**
     * 현재 인증 정보를 제거하는 메서드
     */
    public void unauthenticate() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}