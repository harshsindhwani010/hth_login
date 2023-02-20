package com.login.hth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVerifyDTO {

    public String securityQuestions;
    public String securityQuestions1;
    public String securityQuestions2;




}
