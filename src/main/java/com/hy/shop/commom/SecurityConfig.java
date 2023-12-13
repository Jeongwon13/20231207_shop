package com.hy.shop.commom;

import com.hy.shop.commom.MyUsernamePasswordAuthenticationFilter;
import com.hy.shop.commom.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // SHA256와 솔트를 사용한 PasswordEncoder로 교체
        return new MyPasswordEncoder();
    }

    @Bean
    @Order(1)
    public MySecurityConfigurer mySecurityConfigurer() {
        return new MySecurityConfigurer();
    }

    public class MySecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests(authorizeRequests -> authorizeRequests
                            .antMatchers("/user/**").authenticated()
                            .antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                            .antMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().permitAll()
                    ).formLogin().loginPage("/login")
                    .and()dd
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        }
    }
}
