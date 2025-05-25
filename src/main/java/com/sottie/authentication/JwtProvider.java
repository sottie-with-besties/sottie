package com.sottie.authentication;

import com.sottie.processor.ProcessInfoUtils;
import com.sottie.properties.SottieProperties;
import com.sottie.security.SottieUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.gson.io.GsonDeserializer;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtProvider {

    private final Key secretKey;
    private final Long expireIn;
    private final Long refExpireIn;

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
        refExpireIn = sottieProperties.getAuthentication().getRefreshValidSeconds() * 1000;
    }


    public String generate(SottieAuthentication authentication) {
        return generate(StringUtils.hasText(authentication.getName()) ? authentication.getName() : null, authentication.getProcessId(), authentication.getDetails(), "ROLE_USER");
    }

    public String generate(String userId, String role) {
        return generate(userId, null, null, null, role);
    }

    public String generate(String userId) {
        return generate(userId, null, null, null, "ROLE_USER");
    }

    public String generate(String userId, String processId, Object details, String... roles) {
        processId = StringUtils.hasText(processId) ? processId : ProcessInfoUtils.getCurrentProcessId();
        ClaimsBuilder claimsBuilder = Jwts.claims()
                .id(processId)
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

        List<String> roles = claims.get("roles", List.class);
        SottieAuthentication authentication = SottieAuthentication.builder()
                .processId(claims.getId())
                .name(claims.get("userId").toString())
                .details(claims.get("details"))
                .roles(roles.stream().filter(e-> null != e).map(SimpleGrantedAuthority::new).toList())
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
            log.info("acc-token :: {}", token);
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


    public String generateRefreshToken(SottieAuthentication authentication) {
        String userId = authentication.getName();
        String processId = StringUtils.hasText(authentication.getProcessId()) ? authentication.getProcessId() : ProcessInfoUtils.getCurrentProcessId();

        ClaimsBuilder claimsBuilder = Jwts.claims()
                .add("userId", userId)
                .add("processId", processId);

        Claims claims = claimsBuilder.build();
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + refExpireIn); // Use refreshTokenExpireIn

        String refreshToken = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expireAt)
                .signWith(secretKey)
                .compact();
        log.info("refresh_token :: {}", refreshToken);
        return refreshToken;
    }

    /**
     * Refresh 토큰 검증
     * @param refreshToken 검증할 Refresh 토큰
     * @return 토큰의 유효 여부
     */
    public boolean validateRefreshToken(String refreshToken) {
        try {
            log.info("refresh-token :: {}", refreshToken);
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(refreshToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Refresh JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 Refresh JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 Refresh JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("Refresh JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Claims parseToken(String token) {
        try {
            log.info("token :: {}", token);
            return Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Refresh JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 Refresh JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 Refresh JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("Refresh JWT 토큰이 잘못되었습니다.");
        }
        return null;
    }
}
