package com.ivanboyukliev.schoolgradingapp.security.config;

import com.ivanboyukliev.schoolgradingapp.security.auth.SchoolUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationProviderConfig {

    private final PasswordEncoder passwordEncoder;
    private final SchoolUserDetailsService userDetailsService;

    @Autowired
    public AuthenticationProviderConfig(PasswordEncoder passwordEncoder, SchoolUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
