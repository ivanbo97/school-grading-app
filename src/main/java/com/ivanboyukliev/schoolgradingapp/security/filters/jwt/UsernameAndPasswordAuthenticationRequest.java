package com.ivanboyukliev.schoolgradingapp.security.filters.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;
}
