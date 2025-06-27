package com.sottie.security.interceptor;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.security.Permission;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

    private final JwtProvider tokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
        //Auth anntotation이 있는 경우
        if (null == permission) {
            permission = handlerMethod.getBeanType().getAnnotation(Permission.class);
            if (null == permission) return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasRole = Arrays.stream(permission.roles())
                .anyMatch(role -> authentication != null && authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role))
                );

        if(hasRole) return true;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
//        throw new IllegalAccessException("권한이 없습니다.");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            // 액세스 토큰 추출 및 유효성 검사
            String accessToken = request.getHeader("acc-token");
            if (accessToken != null && tokenProvider.validateToken(accessToken)) {
                // 액세스 토큰이 유효하면 아무 작업도 하지 않음
                log.debug("액세스 토큰이 아직 유효합니다. 토큰 갱신을 건너뜁니다.");
                return;
            }

            // 현재 인증 정보 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 액세스 토큰이 없거나 유효하지 않은 경우 리프레시 토큰 확인
            String refreshToken = request.getHeader("ref-token");

            if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
                // 리프레시 토큰이 유효한 경우, 새 액세스 토큰만 발급
                String newAccessToken = tokenProvider.generate((SottieAuthentication) authentication);
                response.setHeader("acc-token", newAccessToken);
                log.debug("액세스 토큰이 갱신되었습니다.");
            } else if (refreshToken == null || !tokenProvider.validateToken(refreshToken)) {
                // 리프레시 토큰이 없거나 유효하지 않을 경우, 둘 다 새로 발급
                String newAccessToken = tokenProvider.generate((SottieAuthentication) authentication);
                String newRefreshToken = tokenProvider.generateRefreshToken((SottieAuthentication) authentication);
                response.setHeader("acc-token", newAccessToken);
                response.setHeader("ref-token", newRefreshToken);
                log.debug("액세스 토큰과 리프레시 토큰이 모두 갱신되었습니다.");
            }
        } catch (Exception e) {
            // 토큰 갱신 중 오류가 발생해도 요청 처리는 완료되어야 함
            log.error("토큰 갱신 중 오류 발생: {}", e.getMessage());
        } finally {
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        }
    }
}
