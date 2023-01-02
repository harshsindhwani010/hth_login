package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {

    private String firstName;
    private String lastName;
    private String userName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String confirmPassword;
}
