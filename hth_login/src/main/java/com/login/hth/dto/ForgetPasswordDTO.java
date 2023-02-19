package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordDTO {
    private String email; // Changes Done For Testing According to QA FEEDBACK
    private String newPassword;
    private String confirmPassword;

}
