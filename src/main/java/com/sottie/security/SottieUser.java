package com.sottie.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class SottieUser implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 174726374856727L;
    private String userId;
    private String password;
    private String alias;
    private boolean verified;	// 본인 인증 여부
    private boolean locked;
    private Collection<GrantedAuthority> authorities;

    public SottieUser(String userId, String password, String alias, boolean verified, boolean locked, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.alias = alias;
        this.verified = verified;
        this.locked = locked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return locked;
    }
}
