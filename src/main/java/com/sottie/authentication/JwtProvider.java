package com.sottie.authentication;

import com.sottie.properties.SottieProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.*;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    private final Key secretKey;
    private final Long expireIn;

    public JwtProvider(SottieProperties sottieProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(sottieProperties.getAuthentication().getSecretKey());
        secretKey = Keys.hmacShaKeyFor(keyBytes);
        expireIn = sottieProperties.getAuthentication().getAccessValidSeconds() * 1000;
    }

    public String generate(Long userId) {
        return generate(userId, "USER");
    }

    public String generate(Long userId, String... roles) {
        Arrays.stream(roles).map(role -> "ROLE_" + role);
        Claims claims = Jwts.claims()
                .add("userId", userId)
                .add("roles", roles)
                .build();

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

        SottieAuthentication authentication = new SottieAuthentication();
        authentication.setAuthenticated(true);
        authentication.setProcessId(claims.getId());
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
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
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
