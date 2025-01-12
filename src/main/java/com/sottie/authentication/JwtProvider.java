package com.sottie.authentication;

import com.sottie.properties.SottieProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.io.Decoders.*;

@Slf4j
@Component
public class JwtProvider {

    private final Key secretKey;
    private final Long expireIn;

    public JwtProvider(SottieProperties sottieProperties) {
        byte[] keyBytes = null;
        try {
            keyBytes = Decoders.BASE64.decode(sottieProperties.getAuthentication().getSecretKey());
        } catch (IllegalArgumentException e) {
            String defaultSecretKey = "defaultSecretKey12345678901234567890"; // 32바이트 키
            keyBytes = defaultSecretKey.getBytes();
        }

        secretKey = Keys.hmacShaKeyFor(keyBytes);
        expireIn = sottieProperties.getAuthentication().getAccessValidSeconds() * 1000;
    }


    public String generate(SottieAuthentication authentication) {
        return generate(StringUtils.hasText(authentication.getName()) ? Long.parseLong(authentication.getName()) : -1L, authentication.getDetails(), "USER");
    }

    public String generate(Long userId) {
        return generate(userId, null, "USER");
    }

    public String generate(Long userId, Object details, String... roles) {
        Arrays.stream(roles).map(role -> "ROLE_" + role);
        ClaimsBuilder claimsBuilder = Jwts.claims()
                .add("userId", userId)
                .add("roles", roles);

        if (details != null) {
            claimsBuilder.add("details", details);
        }

        Claims claims = claimsBuilder.build();
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expireIn);


        String jwt = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expireAt)
                .signWith(secretKey)
                .compact();
        log.info("jwt :: {}", jwt);
        return jwt;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        SottieAuthentication authentication = SottieAuthentication.builder()
                .processId(claims.getId())
                .name(claims.get("userId").toString())
                .roles(claims.get("roles", List.class))
                .build();
//        authentication.setAuthenticated(true);
//        authentication.setProcessId(claims.getId());
        log.info("token info ::: {}", claims.get("roles"));
        return authentication;
    }


    /**
     * 필터에서 사용할 토큰 검증
     * @param token 필터 정보
     * @return 토큰이 유효 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
