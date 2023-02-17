package com.login.hth.dto;

import lombok.Data;

@Data
public class SecurityDTO {
    private String securityQuestion1;
    private String securityQuestion2;
    private String securityQuestion3;
    private String securityQuestion1Answer;
    private String securityQuestion2Answer;
    private String securityQuestion3Answer;
}
