package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.security.auth.SchoolUserDetailsService;
import com.ivanboyukliev.schoolgradingapp.util.SuccessfulAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    private SchoolUserDetailsService schoolUserDetailsService;

    @Autowired
    public LoginController(SchoolUserDetailsService schoolUserDetailsService) {
        this.schoolUserDetailsService = schoolUserDetailsService;
    }

    @RequestMapping("/login")
    public SuccessfulAuthResponse returnUserNameAfterLogin(Principal user) {
        return new SuccessfulAuthResponse("Successful Authentication");
    }
}
