package com.sottie.config.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;

public class SottieAppRequestContextHolder {
    private static final ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<>();

    public SottieAppRequestContextHolder() {}

    @Nullable
    public static void set(HttpServletRequest request, HttpServletResponse response) {
        currentRequest.set(request);
        currentResponse.set(response);
    }

    @Nullable
    public static HttpServletRequest getRequest() {
        return currentRequest.get();
    }


    @Nullable
    public static HttpServletResponse getResponse() {
        return currentResponse.get();
    }

    @Nullable
    public static void clear() {
        currentRequest.remove();
        currentResponse.remove();
    }

}
