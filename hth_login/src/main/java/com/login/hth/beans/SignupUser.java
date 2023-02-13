package com.login.hth.beans;

import com.login.hth.dto.SecurityDTO;
import com.login.hth.dto.SignupRequestDTO;
import com.login.hth.security.iSeries;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignupUser {
    public List<String[]> insertUserDetails(SignupRequestDTO signupRequestDTO) {
        List<String[]> result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "INSERT INTO QTEMP.USERPROF(usrnme,uemail,uphone,upass1,usec1,usec2,usec3,uans1,uans2,uans3) values('" + signupRequestDTO.getUserName() + "'" +
                ",'" + signupRequestDTO.getEmail() + "','" + signupRequestDTO.getPhoneNo() + "','" + signupRequestDTO.getPassword() + "'" +
                ",'" + signupRequestDTO.getSecurityQuestion1() + "','" + signupRequestDTO.getSecurityQuestion2() + "','" + signupRequestDTO.getSecurityQuestion3() + "'" +
                ",'" + signupRequestDTO.getSecurityQuestion1Answer() + "','" + signupRequestDTO.getSecurityQuestion2Answer() + "','" + signupRequestDTO.getSecurityQuestion3Answer() + "')";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;
    }

    public static List<String[]> securityQuestions(String email){
        List<String[]> result =null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT usec1,usec2 FROM QTEMP.USERPROF ORDER BY RAND() WHERE UEMAIL= '"+ email + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);

        return result;
    }

    public List<String[]> checkUserName(SignupRequestDTO signupRequestDTO) {
        List<String[]> result = null;
        String user = signupRequestDTO.getUserName();
        String mobile = signupRequestDTO.getPhoneNo();
        String email = signupRequestDTO.getEmail();
        String alias = "qtemp.usrprof";
        String file = "testdata.userprof(TRT)";
        String sql = "SELECT * FROM qtemp.usrprof where USRNME='" + user + "' or UEMAIL='" + email + "' or UPHONE = '" + mobile + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;
    }
}
