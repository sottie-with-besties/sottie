package com.sottie.security.interceptor;

import com.sottie.security.Permission;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;

public class PermissionInterceptor implements HandlerInterceptor {

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
                .anyMatch(role -> authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role))
                );

        if(hasRole) return true;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
//        throw new IllegalAccessException("권한이 없습니다.");
    }

}
