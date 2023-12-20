package com.hy.shop.commom.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@ConfigurationProperties(prefix = "kakao")
@Getter
@Setter
public class KakaoProperties {
    private String apiKey;
    private String redirectUri;
    private Profiles profiles;


    public static class Profiles {
        private String include;
    }
}


