package com.ivanboyukliev.schoolgradingapp.security.config;

import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.JWTTokenValidationFilter;
import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.JwtPropertyHolder;
import com.ivanboyukliev.schoolgradingapp.security.filters.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtPropertyHolder jwtPropertyHolder;

    @Autowired
    public SecurityConfig(DaoAuthenticationProvider daoAuthenticationProvider, JwtPropertyHolder jwtPropertyHolder) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtPropertyHolder = jwtPropertyHolder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**","/swagger-ui/**", "/v3/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), this.jwtPropertyHolder))
                .addFilterAfter(new JWTTokenValidationFilter(this.jwtPropertyHolder), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(STUDENT_BASE_URL + "/{studentId}")
                .access("@permissionsRegulator.isUserAuthorizedToAccess(authentication,#studentId)")
                .antMatchers(REPORT_BASE_URL + GET_AVG_MARK_FOR_STUD_IN_COURSES)
                .access("@permissionsRegulator.isUserAuthorizedToAccess(authentication,#studentId)")
                .antMatchers(REPORT_BASE_URL + GET_AVG_MARK_FOR_STUD_IN_COURSE)
                .access("@permissionsRegulator.isUserAuthorizedToAccess(authentication,#studentId)")
                .and()
                .authorizeRequests()
                .antMatchers("/api-docs", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated();

    }
}
