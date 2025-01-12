package com.sottie.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class SottieGlobalWrapperFilter extends GenericFilterBean implements Ordered {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("SottieGlobalWrapperFilter.doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Swagger 경로 제외
        String requestURI = req.getRequestURI();
        if (requestURI.startsWith("/swagger-ui/") || requestURI.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response); // 필터 건너뛰기
            return;
        }

        try {
            SottieAppRequestContextHolder.set(new HttpServletRequestWrapper(req), new HttpServletResponseWrapper(res));
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
            chain.doFilter(request, responseWrapper);
//            chain.doFilter(request, responseWrapper);
            responseWrapper.copyBodyToResponse();
        } finally {
            SottieAppRequestContextHolder.clear();
        }
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}