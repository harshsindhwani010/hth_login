package com.login.hth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidateDTO {
    private String otp;
    private String userName;
    private String password;
}
