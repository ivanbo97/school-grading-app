package com.ivanboyukliev.schoolgradingapp.security.auth;

import com.ivanboyukliev.schoolgradingapp.domain.SchoolSystemCredential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class SchoolUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<? extends SimpleGrantedAuthority> authorities;

    //== constants ==
    private static final boolean TRUE = true;

    //== fields ==
    //---------------------------------------------
    private boolean isAccountNonExpired = TRUE;      //todo figure out how to implement if needed
    private boolean isAccountNonLocked = TRUE;       //todo figure out how to implement if needed
    private boolean isCredentialsNonExpired = TRUE;  //todo figure out how to implement if needed
    private boolean isEnabled = TRUE;                //todo figure out how to implement if needed
    //---------------------------------------------


    public SchoolUserDetails(SchoolSystemCredential credential) {
        username = credential.getUsername();
        password = credential.getPassword();
        authorities = credential.getUserRole().getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
