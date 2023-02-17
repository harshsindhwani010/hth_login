package com.login.hth.beans;

import com.login.hth.dto.SecurityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


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

    public ResponseEntity<Object> checkAns(String email) {
        String[] securityAnswers = SignupUser.questionAnswer(email);
        SecurityDTO wholeDTOList = new SecurityDTO();

        wholeDTOList.setSecurityQuestion1(securityAnswers[0].trim());
        wholeDTOList.setSecurityQuestion1Answer(securityAnswers[3].trim());
        wholeDTOList.setSecurityQuestion2(securityAnswers[1].trim());
        wholeDTOList.setSecurityQuestion2Answer(securityAnswers[4].trim());
        wholeDTOList.setSecurityQuestion3(securityAnswers[2].trim());
        wholeDTOList.setSecurityQuestion3Answer(securityAnswers[5].trim());

        return new ResponseEntity(wholeDTOList, HttpStatus.OK);
    }

}
