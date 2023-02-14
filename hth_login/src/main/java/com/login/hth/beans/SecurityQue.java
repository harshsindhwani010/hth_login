package com.login.hth.beans;

import com.login.hth.dto.SecurityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityQue {
    public ResponseEntity<Object> checkSecurity(String email){
        List<String[]> securityQuestions = SignupUser.securityQuestions(email);
        List<SecurityDTO> wholeDTOList = new ArrayList<SecurityDTO>();

        for(int i=0;i<securityQuestions.size();i++) {
            String[] que = securityQuestions.get(i);

            SecurityDTO securityDTO = new SecurityDTO();
            securityDTO.setSecurityQuestion1(que[0]);
            securityDTO.setSecurityQuestion1(que[1]);

            wholeDTOList.add(securityDTO);

        }
        return ResponseEntity.accepted().body(wholeDTOList);
    }
}