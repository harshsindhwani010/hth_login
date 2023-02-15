package com.login.hth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVerifyDTO {

    private String securityQuestion1;
    private String securityQuestion2;

    private String securityQuestion1Answer;
    private String securityQuestion2Answer;

}
