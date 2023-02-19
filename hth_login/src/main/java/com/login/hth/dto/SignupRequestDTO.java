package com.login.hth.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {

    private String userName;
    private String phoneNo;
    private String employPolicy;
    private String email;
    private String password;
    private String confirmPassword;
    private String securityQuestion1;
    private String securityQuestion2;
    private String securityQuestion3;
    private String securityQuestion1Answer;
    private String securityQuestion2Answer;
    private String securityQuestion3Answer;
}
