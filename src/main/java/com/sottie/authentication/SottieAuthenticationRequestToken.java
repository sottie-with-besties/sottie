package com.sottie.authentication;

import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SottieAuthenticationRequestToken {
    private String userName;
}
