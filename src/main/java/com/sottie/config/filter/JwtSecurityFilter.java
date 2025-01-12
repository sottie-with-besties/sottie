package com.sottie.config.filter;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.processor.ProcessInfoUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends GenericFilterBean {

    private static final String ACCESS_TOKEN = "acc-token";
    private static final String REFRESH_TOKEN = "ref-token";

    private final JwtProvider tokenProvider;

    // TODO: 토큰 비교 후 토큰 정보 없을 시 빈 토큰 생성 후 주입
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("JwtSecurityFilter.doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String jwtToken = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        if(StringUtils.hasText(jwtToken)) {
            //토큰 값에서 Authentication 값으로 가공해서 반환 후 저장
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {

            ((HttpServletResponse) response).setHeader(ACCESS_TOKEN, tokenProvider.generate(-1L, "ANONYMOUS"));
            ((HttpServletResponse) response).setHeader(REFRESH_TOKEN, tokenProvider.generate(-1L, "ANONYMOUS"));
            SecurityContextHolder.getContext().setAuthentication(SottieAuthentication.builder()
                                                                .processId(ProcessInfoUtils.getCurrentProcessId())
                                                                .roles(List.of(() -> "ROLE_ANONYMOUS"))
                                                                .build());
            log.info("유효한 JWT 토큰이 없습니다. requestURI : {}", requestURI);
        }
        filterChain.doFilter(request, response);
    }


    /**
     * HttpServletRequest에서 `Authorization` 헤더를 받음.
     * 헤더에서 'Bearer'로 시작하는 토큰이 있으면 'Bearer' 부분 제거하고 토큰 값 반환 아니면 널 값 반환
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String accToken = request.getHeader(ACCESS_TOKEN);
        // TODO: access token 검증 처리 구현
        if (null != accToken && tokenProvider.validateToken(accToken)) return accToken;

        String refToken = request.getHeader(REFRESH_TOKEN);
        // TODO: refresh token 처리
        return null;
    }
}
