package com.ivanboyukliev.schoolgradingapp.security.filters.jwt.config;

import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.TokenExpirationAfterDays;
import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.TokenPrefix;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.SecretKey;

@Configuration
@ComponentScan(basePackages = "com.ivanboyukliev.schoolgradingapp.security")
@PropertySource("classpath:application.properties")
public class JwtConfig {

    @Value("${application.jwt.secretKey:secret123*}")
    private String secretKey;

    @Value("${application.jwt.tokenPrefix:Bearer}")
    private String tokenPrefix;

    @Value("${application.jwt.tokenExpirationAfterDays:5}")
    private Long tokenExpirationDays;


    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Bean
    @TokenExpirationAfterDays
    public Long tokenExpirationAfterDays() {
        return tokenExpirationDays;
    }

    @Bean
    @TokenPrefix
    public String tokenPrefix() {
        return tokenPrefix;
    }
}
