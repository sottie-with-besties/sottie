package com.sottie.config.filter;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.authentication.SottieAuthenticationRequestToken;
import com.sottie.processor.ProcessInfoUtils;
import com.sottie.processor.SottieAuthenticationProcessor;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private static final String ACCESS_TOKEN = "acc-token";
    private static final String REFRESH_TOKEN = "ref-token";

    private final JwtProvider tokenProvider;

    private final SottieAuthenticationProcessor sottieAuthenticationProcessor;

    // TODO: 토큰 비교 후 토큰 정보 없을 시 빈 토큰 생성 후 주입
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtSecurityFilter.doFilter");

        boolean isToken = resolveToken(request);
        String jwtToken = request.getHeader(ACCESS_TOKEN);
        if(!isToken) {
            response.setHeader(ACCESS_TOKEN, tokenProvider.generate("-1", "ANONYMOUS"));
            response.setHeader(REFRESH_TOKEN, tokenProvider.generate("-1", "ANONYMOUS"));
            SecurityContextHolder.getContext().setAuthentication(SottieAuthentication.builder()
                                                                .processId(ProcessInfoUtils.getCurrentProcessId())
                                                                .roles(List.of(() -> "ROLE_ANONYMOUS"))
                                                                .build());
            log.info("유효한 JWT 토큰이 없습니다. requestURI : {}", request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }


    /**
     * HttpServletRequest에서 `Authorization` 헤더를 받음.
     * 헤더에서 'Bearer'로 시작하는 토큰이 있으면 'Bearer' 부분 제거하고 토큰 값 반환 아니면 널 값 반환
     * @param request
     * @return
     */
    private boolean resolveToken(HttpServletRequest request) {
        String accToken = request.getHeader(ACCESS_TOKEN);
        // TODO: access token 검증 처리 구현

        if (null != accToken && tokenProvider.validateToken(accToken)) {
            Authentication authentication = tokenProvider.getAuthentication(accToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), request.getRequestURI());
            return true;
        };

        String refToken = request.getHeader(REFRESH_TOKEN);
        // TODO: refresh token 처리
        if (null != refToken && tokenProvider.validateRefreshToken(refToken)) {

            Claims claims = tokenProvider.parseToken(refToken);

            SottieAuthenticationRequestToken authenticationRequestToken = SottieAuthenticationRequestToken.builder()
                    .userName(claims.get("userId").toString())
                    .build();
            log.info("sottieAuthentication.getName() ::: {}", authenticationRequestToken.getUserName());
            sottieAuthenticationProcessor.authenticate(authenticationRequestToken);
            return true;
        }

        return false;
    }
}
