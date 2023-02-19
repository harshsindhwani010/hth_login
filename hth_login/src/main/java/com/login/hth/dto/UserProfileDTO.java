package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String dateOfBirth;
    private String phoneNo;
    private String email;
    private String policy;

}
