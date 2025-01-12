package com.sottie.authentication;

import com.sottie.processor.ProcessInfoUtils;
import com.sottie.processor.SottieAuthenticationProcessor;
import com.sottie.properties.SottieProperties;
import com.sottie.utils.SottieWebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SottieAuthenticationManager implements AuthenticationManager {

    private final SottieProperties sottieProperties;
    private final SottieAuthenticationProcessor<SottieAuthenticationRequestToken, SottieAuthentication> sottieAuthenticationProcessor;
    private final JwtProvider tokenProvider;

    private static final String ACCESS_TOKEN = "acc-token";
    private static final String REFRESH_TOKEN = "ref-token";
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication :: {}", authentication);
        SottieAuthentication sottieAuthentication = SottieAuthentication.builder()
                .processId(ProcessInfoUtils.getCurrentProcessId())
                .name(authentication.getName())
                .roles(List.of(() -> "ROLE_USER"))
                .build();
//        SottieAuthentication sottieAuthentication = BeanUtils.instantiateClass(SottieAuthentication.class);
        SottieWebUtils.getResponse().setHeader(ACCESS_TOKEN, tokenProvider.generate(sottieAuthentication));
        SecurityContextHolder.getContext().setAuthentication(sottieAuthentication);
        return sottieAuthentication;
    }

    public Authentication authenticate(SottieAuthenticationRequestToken authenticationRequestToken) throws AuthenticationException {
        try {
            Authentication authentication =  sottieAuthenticationProcessor.authenticate(authenticationRequestToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception e){
            log.error("authenticate error ::: {}", e.getMessage());
            return null;
        }
    }

    public void unauthenticate() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
