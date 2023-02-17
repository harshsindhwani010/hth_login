package com.login.hth.beans;

import com.login.hth.dto.QuestionsAnswer;
import com.login.hth.dto.SecurityDTO;
import com.login.hth.dto.SecurityQuestionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SecurityQue {
    public ResponseEntity<Object> checkSecurity(String email) {
        String[] securityQuestions = SignupUser.securityQuestions(email);
        String[] securityQuestions2 = new String[securityQuestions.length];
        for (int i = 0; i < securityQuestions.length; i++) {
            securityQuestions2[i] = securityQuestions[i].trim();
        }
        return new ResponseEntity(securityQuestions2, HttpStatus.OK);
    }

    public ResponseEntity<Object> checkAns(SecurityQuestionDTO securityQuestionDTO, String email) {
        String[] securityAnswers = SignupUser.questionAnswer(email);
        System.out.println(securityQuestionDTO);
        List<QuestionsAnswer> questionsAnswers = securityQuestionDTO.getSecurityQuestions();
        for (int i = 0; i < securityAnswers.length; i++) {
//            securityAnswers[i] = .get(i);
        }
        return new ResponseEntity(securityQuestionDTO, HttpStatus.OK);
    }

}
