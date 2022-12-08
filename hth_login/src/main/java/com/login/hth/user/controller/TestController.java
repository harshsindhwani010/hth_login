package com.login.hth.user.controller;

import com.login.hth.user.beans.OtpValidate;
import com.login.hth.user.beans.UserLogin;
import com.login.hth.user.dto.JwtResponse;
import com.login.hth.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2")
public class TestController {

    @Autowired
    UserLogin userLogin;

    @Autowired
    private JWTUtility jwtUtility;

    @GetMapping("/test")
    public String test(HttpServletRequest httpServletRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        System.out.println(user);
        userLogin.checkHttp(httpServletRequest);
        return "This is token authentication api.";
    }

    @PostMapping("/getUserName")
    public String getUserName(@RequestBody JwtResponse response){
        System.out.println(jwtUtility.getExpirationDateFromToken(response.getToken()));
        return jwtUtility.getUsernameFromToken(response.getToken());
    }

}
