package com.hy.shop.commom.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Slf4j
@Component
@ConfigurationProperties(prefix = "naver")
@Getter
@Setter
public class NaverConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestTokenUri;
    private String baseUrl;
    private String state;


}
