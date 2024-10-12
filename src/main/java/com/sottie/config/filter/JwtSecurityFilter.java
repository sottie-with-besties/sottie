package com.sottie.config.filter;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
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

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProvider tokenProvider;

    // TODO: 토큰 비교 후 토큰 정보 없을 시 빈 토큰 생성 후 주입
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.debug("JwtSecurityFilter.doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String jwtToken = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        if(StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {
            //토큰 값에서 Authentication 값으로 가공해서 반환 후 저장
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {

            ((HttpServletResponse) response).setHeader(AUTHORIZATION_HEADER, "Bearer " + tokenProvider.generate(-1L, "ANONYMOUS"));
            SecurityContextHolder.getContext().setAuthentication(new SottieAuthentication());
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
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) return bearerToken.substring(7);

        return null;
    }
}
