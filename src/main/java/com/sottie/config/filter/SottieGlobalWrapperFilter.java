package com.sottie.config.filter;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.processor.ProcessInfoUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class SottieGlobalWrapperFilter  extends GenericFilterBean implements Ordered {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("SottieGlobalWrapperFilter.doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            SottieAppRequestContextHolder.set(new HttpServletRequestWrapper(req), new HttpServletResponseWrapper(res));
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
            filterChain.doFilter(request, responseWrapper);
            responseWrapper.copyBodyToResponse();
        } finally {
            SottieAppRequestContextHolder.clear();
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}