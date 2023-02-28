package com.login.hth.beans;

import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.QuestionsAnswerDTO;
import com.login.hth.dto.SecurityQuestionDTO;
import com.login.hth.security.iSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityDetails {


    public ResponseEntity<Object> updateSecurity(SecurityQuestionDTO securityQuestionDTO, String email) {
        MessageDTO er = new MessageDTO();
        List<String[]> result = null;
        List<QuestionsAnswerDTO> list = securityQuestionDTO.getSecurityQuestions();
//        List<QuestionsAnswerDTO> list1 = securityQuestionDTO.getSecurityAnswer();
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "UPDATE QTEMP.USERPROF set usec1 = '"+list.get(0).getQuestion()+"' , usec2 = '"+list.get(1).getQuestion()+"',usec3 = '"+list.get(2).getQuestion()+"',uans1 = '"+list.get(0).getAnswer()+"',uans2 = '"+list.get(1).getAnswer()+"',uans3 = '"+list.get(2).getAnswer()+"'   WHERE UEMAIL= '" + email + "' or usrnme='" + email + "'";
//        System.out.println(sql);
        result = iSeries.executeSQLByAlias(sql, alias, file);
        if (result != null) {

            er.setMessage("Data Updated");
            return new ResponseEntity<>(er, HttpStatus.OK);
        } else {
            er.setMessage("Bad Request.");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }





}
