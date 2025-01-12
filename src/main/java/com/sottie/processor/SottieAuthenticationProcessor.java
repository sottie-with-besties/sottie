package com.sottie.processor;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthentication;
import com.sottie.authentication.SottieAuthenticationRequestToken;
import com.sottie.config.SottieUserDetailProvider;
import com.sottie.properties.SottieProperties;
import com.sottie.security.SottieUser;
import com.sottie.utils.SottieWebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SottieAuthenticationProcessor<S extends SottieAuthenticationRequestToken, R extends SottieAuthentication> implements ApplicationContextAware {

    protected Class<R> authenticateClass;

    protected SottieProperties authenticationProperties;
    protected SottieUserDetailProvider userDetailProvider;

    protected JwtProvider tokenProvider;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.authenticationProperties = new SottieProperties();
        if (this.authenticationProperties != null) {
            this.tokenProvider = new JwtProvider(this.authenticationProperties);
            applicationContext.getAutowireCapableBeanFactory().autowireBean(this.authenticationProperties);
        }

        this.userDetailProvider = new SottieUserDetailProvider();
        if (this.userDetailProvider != null) {
            applicationContext.getAutowireCapableBeanFactory().autowireBean(this.userDetailProvider);
        }

        this.authenticateClass = (Class<R>) SottieAuthentication.class;
        if (this.userDetailProvider != null) {
            applicationContext.getAutowireCapableBeanFactory().autowireBean(this.authenticateClass);
        }

        if (this.tokenProvider != null) {
            applicationContext.getAutowireCapableBeanFactory().autowireBean(this.tokenProvider);
        }
    }

    public R authenticate(@NonNull S authenticationRequestToken) {
        R authentication = this.creeateAuthenticationToken(authenticationRequestToken);
        SottieUser user = this.userDetailProvider.getDetails(authenticationRequestToken);
        authentication.setDetails(user);
        authentication.setAuthenticated(true);

        String token = tokenProvider.generate(authentication);
        setToken(token);

        return authentication;
    }

    protected R creeateAuthenticationToken(S authenticationRequestToken) {
        R authentication = BeanUtils.instantiateClass(this.authenticateClass);
        authentication.setProcessId(ProcessInfoUtils.getCurrentProcessId());
        return authentication;
    }

    protected void setToken(String token) {
        SottieWebUtils.getResponse().setHeader(this.authenticationProperties.getAuthentication().getTokenKey(), token);
    }
}
