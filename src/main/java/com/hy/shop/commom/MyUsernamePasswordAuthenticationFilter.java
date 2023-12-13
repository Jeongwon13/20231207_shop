package com.hy.shop.commom;

import com.hy.shop.commom.util.SHA256;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Custom filter to handle SHA-256 password hashing
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    public MyUsernamePasswordAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getMethod().equals("POST")) {
            String username = obtainUsername(request);
            String rawPassword = obtainPassword(request);
            String hashedPassword = SHA256.getEncrypt(rawPassword, getSalt()); // Change this line to use SHA-256
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, hashedPassword);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        return super.attemptAuthentication(request, response);
    }

    private String getSalt() {
        // You may implement salt generation logic here
        return SHA256.generateSalt();
    }
}
