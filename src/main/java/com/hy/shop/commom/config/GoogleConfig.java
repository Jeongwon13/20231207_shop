package com.hy.shop.commom.config;


import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;


@Slf4j
@Component
@ConfigurationProperties(prefix = "google")
@Getter
@Setter
public class GoogleConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUrl; //https://oauth2.googleapis.com/token
    private String tokenResUrl;

    private String scope;
    private String accessToken;
    private String expires_in;
    private String refresh_token;
    private String token_type;
    private String id_token;
}


