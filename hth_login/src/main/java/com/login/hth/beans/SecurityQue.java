package com.login.hth.beans;

import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.QuestionsAnswer;
import com.login.hth.dto.SecurityQuestionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;


@Service
public class SecurityQue {
    public ResponseEntity<Object> checkSecurity(String email) {
        String[] securityQuestions = SignupUser.securityQuestions(email);
        String[] securityQuestions2 = new String[securityQuestions.length];
        for (int i = 0; i < securityQuestions.length; i++) {
            securityQuestions2[i] = securityQuestions[i].trim();
        }
        return new ResponseEntity(securityQuestions2, HttpStatus.OK);
    }

    public ResponseEntity<Object> securityAns(SecurityQuestionDTO securityQuestionDTO, String email) {
        boolean pass= false;
        MessageDTO messageDTO = new MessageDTO();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String[] securityAnswers = SignupUser.questionAnswer(email);
        securityAnswers=Arrays.stream(securityAnswers).map(String::trim).toArray(String[]::new);
        System.out.println(securityQuestionDTO);
        List<QuestionsAnswer> questionsAnswers = securityQuestionDTO.getSecurityQuestions();
        for(QuestionsAnswer questionsAnswer : securityQuestionDTO.getSecurityQuestions()){
            int questionIndex = Arrays.asList(securityAnswers).indexOf(questionsAnswer.getQuestion());
            int answerIndex =questionIndex+3;
            if(questionsAnswer.getAnswer().trim().equals(securityAnswers[answerIndex]) && questionsAnswer.getQuestion().trim().equals(securityAnswers[questionIndex])){
                pass=true;
            }else{
                pass=false;
                break;
            }
        }
        if(pass){
            messageDTO.setMessage("success");
            httpStatus= HttpStatus.OK;
        }else {
            messageDTO.setMessage("Answer Not Matched");
            httpStatus= HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(messageDTO,httpStatus);
    }

}

