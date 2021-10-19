package com.ivanboyukliev.schoolgradingapp.security.config;

import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.JWTTokenGeneratorFilter;
import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.JWTTokenValidationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JWTTokenValidationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(STUDENT_BASE_URL + "/{studentId}")
                .access("@permissionsRegulator.isUserAuthorizedToAccess(authentication,#studentId)")
                .antMatchers(REPORT_BASE_URL + GET_AVG_MARK_FOR_STUD_IN_COURSES)
                .access("@permissionsRegulator.isUserAuthorizedToAccess(authentication,#studentId)")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
