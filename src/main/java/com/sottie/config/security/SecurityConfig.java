package com.sottie.config.security;

import com.sottie.authentication.JwtProvider;
import com.sottie.authentication.SottieAuthenticationManager;
import com.sottie.config.filter.JwtSecurityFilter;
import com.sottie.properties.SottieProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties({SottieProperties.class})
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider tokenProvider;

    private final AntPathRequestMatcher[] WHITE_LIST_URL = {
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/sottie/users/**")
    };

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                // 세션 사용하지 않
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증 절차 없이 접근 가능한 화이트 리스트 추가
                .authorizeHttpRequests(request -> request.requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/sottie/**")).hasAnyRole("ANONYMOUS", "USER")
                        .anyRequest()
                        .authenticated())
                // JWT 토큰 필터 추가
                .addFilterBefore(
                        new JwtSecurityFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SottieAuthenticationManager authenticationManager(SottieProperties properties) throws Exception {
        return new SottieAuthenticationManager(properties);
    }
}
