package com.sottie.utils;

import com.google.gson.Gson;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.security.SottieUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SottieUserUtils {
    static public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    static public Integer getUserIdInt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(authentication.getName());
    }

    static public String getProcessId() {
        SottieAuthentication authentication = (SottieAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getProcessId();
    }

    static public SottieUser getDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Gson gson = new Gson();
        String json = gson.toJson(authentication.getDetails()); // LinkedTreeMap을 JSON 문자열로 변환
        SottieUser sottieUser = gson.fromJson(json, SottieUser.class); // JSON을 SottieUser로 변환
        return sottieUser;
    }
}
