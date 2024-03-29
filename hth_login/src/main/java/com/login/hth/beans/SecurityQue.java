package com.login.hth.beans;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.login.hth.dto.*;
import org.apache.tomcat.util.json.JSONFilter;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class SecurityQue {

    public ResponseEntity<Object> getSecurityQuestions(String email) {
        String[] tempQuestion = SignupUser.securityQuestions(email);
        String[] securityQuestionss = new String[tempQuestion.length];
        for (int i = 0; i < tempQuestion.length; i++) {
            securityQuestionss[i] = tempQuestion[i].trim();
        }
        return new ResponseEntity(securityQuestionss, HttpStatus.OK);
    }


    public JSONObject questions() {
        JSONObject jsonObject = new JSONObject();
     List<String> question = Arrays.asList("What Is Your Best Friend's Name","What Is Your Favorite Colour","What Is Your Favorite Food","What Is Your First Name","What Is Your Last Name");
     jsonObject.put("questions", question);

        return jsonObject;
    }

    public ResponseEntity<Object> securityAnswers(String email) {
        String[] tempAnswer = SignupUser.securityAnswers(email);
        String[] securityAnswer = new String[tempAnswer.length];
        for (int i = 0; i < tempAnswer.length; i++) {
            securityAnswer[i] = tempAnswer[i].trim();
        }
        return new ResponseEntity(securityAnswer, HttpStatus.OK);
    }

    public ResponseEntity<Object> securityAns(SecurityQuestionDTO securityQuestionDTO, String email) {
        boolean pass= false;
        MessageDTO messageDTO = new MessageDTO();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String[] securityAnswers = SignupUser.questionAnswer(email);
        securityAnswers=Arrays.stream(securityAnswers).map(String::trim).toArray(String[]::new);
        System.out.println(securityQuestionDTO);
        List<QuestionsAnswerDTO> questionsAnswerDTOS = securityQuestionDTO.getSecurityQuestions();
        for(QuestionsAnswerDTO questionsAnswerDTO : securityQuestionDTO.getSecurityQuestions()){
            int questionIndex = Arrays.asList(securityAnswers).indexOf(questionsAnswerDTO.getQuestion());
            int answerIndex =questionIndex+3;
            if(questionsAnswerDTO.getAnswer().trim().equals(securityAnswers[answerIndex]) && questionsAnswerDTO.getQuestion().trim().equals(securityAnswers[questionIndex])){
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

