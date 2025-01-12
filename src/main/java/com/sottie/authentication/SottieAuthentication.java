package com.sottie.authentication;

import com.sottie.security.SottieUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SottieAuthentication implements Authentication {

    private String processId;
    private String name;
    private Object details;
    private List<? extends GrantedAuthority> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == null) return List.of(() -> "ROLE_ANONYMOUS");
        return this.roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getProcessId() {
        return this.processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public void setAuthorities(List<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    public void setDetails(Object details) {
        if (!(details instanceof SottieUser)) {
            throw new IllegalArgumentException("허용되지 않은 사용자 유형");
        }
        this.details = details;
    }

//    public void addAuthorities(Collection<? extends GrantedAuthority> roles) {
//    }

}
