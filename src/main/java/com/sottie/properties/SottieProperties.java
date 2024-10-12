package com.sottie.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value="sottie")
public class SottieProperties {
    private Authentication authentication = new Authentication();

    @Data
    @EqualsAndHashCode
    public static class Authentication {
        private String tokenKey = "acc-token";
        private String refreshTokenKey = "ref-token";
        private String secretKey = null;
        private Long accessValidSeconds = 60L;
        private Long refreshValidSeconds = 1800L;
    }
}
