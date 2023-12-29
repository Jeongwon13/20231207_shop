package com.hy.shop.commom.config;

import com.hy.shop.commom.interceptor.ItemTypeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@ComponentScan("com.hy.shop.commom.interceptor")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ItemTypeInterceptor itemTypeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(itemTypeInterceptor)
                .addPathPatterns("/**");

    }

}
