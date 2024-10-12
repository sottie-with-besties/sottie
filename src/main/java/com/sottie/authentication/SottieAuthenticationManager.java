package com.sottie.authentication;

import com.sottie.properties.SottieProperties;
import com.sottie.security.SottieUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class SottieAuthenticationManager implements AuthenticationManager {

    public SottieAuthenticationManager(SottieProperties properties) {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication :: {}", authentication);
        SottieAuthentication sottieAuthentication = BeanUtils.instantiateClass(SottieAuthentication.class);

        SecurityContextHolder.getContext().setAuthentication(sottieAuthentication);
        return sottieAuthentication;
    }

    public void unauthenticate() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
