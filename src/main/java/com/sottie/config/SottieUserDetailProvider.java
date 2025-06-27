package com.sottie.config;

import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.authentication.SottieAuthenticationRequestToken;
import com.sottie.security.SottieUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SottieUserDetailProvider {

    @Autowired
    private UserRepository userRepository;
    public SottieUser getDetails(SottieAuthenticationRequestToken token) {
        return getUser(token.getUserName());
    }

    private SottieUser getUser(String userName) {
        SottieUser user = null;
        // TODO 토큰정보 조회
        Optional<User> info = userRepository.findById(Long.parseLong(userName));
        user = SottieUser.builder()
                .userId(userName)
                .authorities(List.of(() -> "ROLE_USER"))
                .verified(info.get().isIdentityVerification())
                .build();
        return user;
    }
}
