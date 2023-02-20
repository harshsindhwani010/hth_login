package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsDTO {

    List<QuestionsAnswerDTO> securityQuestions;
    private String question1 = "Question1";
    private String question2 = "Question2";
    private String question3 = "Question3";

}
