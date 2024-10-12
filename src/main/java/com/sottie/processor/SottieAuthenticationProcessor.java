package com.sottie.processor;

import com.sottie.authentication.SottieAuthentication;
import com.sottie.properties.SottieProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SottieAuthenticationProcessor<S extends SottieAuthentication, R extends SottieAuthentication> implements ApplicationContextAware {

    Class<R> authenticateClass;

    protected SottieProperties authenticationProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.authenticationProperties = new SottieProperties();
        if (this.authenticationProperties != null) {
           applicationContext.getAutowireCapableBeanFactory().autowireBean(this.authenticationProperties);
        }
    }

    protected R creeateAuthenticationToken(S authenticationRequestToken) {
        R authentication = BeanUtils.instantiateClass(this.authenticateClass);
        authentication.setProcessId(ProcessInfoUtils.getCurrentProcessId());
        return authentication;
    }
}
