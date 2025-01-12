package com.sottie.config;

import com.sottie.authentication.SottieAuthenticationRequestToken;
import com.sottie.security.SottieUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SottieUserDetailProvider {

    public SottieUser getDetails(SottieAuthenticationRequestToken token) {
        return getUser(token.getUserName());
    }

    private SottieUser getUser(String userName) {
        SottieUser user = null;
        // TODO 토큰정보 조회
        user = SottieUser.builder()
                .userId(userName)
                .authorities(List.of(()->"ROLE_USER"))
                .build();
        return user;
    }
}
