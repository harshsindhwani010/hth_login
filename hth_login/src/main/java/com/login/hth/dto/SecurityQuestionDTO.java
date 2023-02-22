package com.login.hth.dto;

import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityQuestionDTO {

    List<QuestionsAnswerDTO> securityQuestions;
    List<QuestionsAnswerDTO> securityAnswer;

}
