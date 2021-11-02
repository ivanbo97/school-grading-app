package com.ivanboyukliev.schoolgradingapp.security.filters.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Component
public class JwtPropertyHolder {

    private final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private final String tokenPrefix;
    private final Long expirationDays;
    private final SecretKey secretKey;

    @Autowired
    public JwtPropertyHolder(@TokenPrefix String tokenPrefix,
                             @TokenExpirationAfterDays Long expirationDays,
                             SecretKey secretKey) {
        this.tokenPrefix = tokenPrefix;
        this.expirationDays = expirationDays;
        this.secretKey = secretKey;
    }
}
