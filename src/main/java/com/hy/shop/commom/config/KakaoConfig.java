package com.hy.shop.commom.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Slf4j
@Component
@ConfigurationProperties(prefix = "kakao")
@Getter
@Setter
public class KakaoConfig {
    private String apiKey;
    private String redirectUri;
    private Profiles profiles;
    private String clientSecret;

    public static class Profiles {
        private String include;
    }


}


