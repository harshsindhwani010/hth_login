package com.login.hth.beans;

import com.login.hth.dto.SignupRequestDTO;
import com.login.hth.security.iSeries;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignupUser {
    public List<String[]> insertUserDetails(SignupRequestDTO signupRequestDTO) {
        List<String[]> result = null;
        String[] aliases = {"QTEMP.INSURE","QTEMP.USERPROF"};
        String[] files = {"TESTDATA.INSURE(TRT)","TESTDATA.USERPROF(TRT)"};
        String sql = "SELECT IFNAM,ILNAM,IDOB,ISSN FROM QTEMP.INSURE JOIN qtemp.userprof on ISSN=USSN  WHERE  IEMPID ='" + signupRequestDTO.getEmployPolicy() + "' or IPOLCY = '" + signupRequestDTO.getEmployPolicy() + "' or ISSN='" + signupRequestDTO.getEmployPolicy() + "'";
        result =iSeries.executeSQLByAlias(sql,aliases,files);
        if(result.size()>0) {
        return null;
        }else {
             sql = "SELECT IFNAM,ILNAM,IDOB,ISSN FROM QTEMP.INSURE WHERE  IEMPID ='" + signupRequestDTO.getEmployPolicy() + "' or IPOLCY = '" + signupRequestDTO.getEmployPolicy() + "' or ISSN='" + signupRequestDTO.getEmployPolicy() + "'";
            result =iSeries.executeSQLByAlias(sql,aliases,files);
            if(result.size()==0){
                return null;
            }
            String ssn = result.get(0)[3].trim();
            String alias = "QTEMP.USERPROF";
            String file = "TESTDATA.USERPROF(TRT)";
            sql = "INSERT INTO QTEMP.USERPROF(usrnme,uemail,uphone,USSN,upass1,usec1,usec2,usec3,uans1,uans2,uans3) values('" + signupRequestDTO.getUserName() + "'" +
                    ",'" + signupRequestDTO.getEmail() + "','" + signupRequestDTO.getPhoneNo() + "','" + ssn + "','" + signupRequestDTO.getPassword() + "'" +
                    ",'" + signupRequestDTO.getSecurityQuestion1() + "','" + signupRequestDTO.getSecurityQuestion2() + "','" + signupRequestDTO.getSecurityQuestion3() + "'" +
                    ",'" + signupRequestDTO.getSecurityQuestion1Answer() + "','" + signupRequestDTO.getSecurityQuestion2Answer() + "','" + signupRequestDTO.getSecurityQuestion3Answer() + "')";
            result = iSeries.executeSQLByAlias(sql,alias,file);
            return result;
        }
    }

    public static String[] securityQuestions(String email) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT usec1,usec2,usec3 FROM QTEMP.USERPROF WHERE UEMAIL= '" + email + "' or USRNME= '" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);

        return result;
    }
    public static String[] securityAnswers(String email) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT uans1,uans2,uans3 FROM QTEMP.USERPROF WHERE UEMAIL= '" + email + "' or USRNME= '" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);

        return result;
    }


    public static String[] questionAnswer(String email) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT usec1,usec2,usec3,uans1,uans2,uans3 FROM QTEMP.USERPROF WHERE UEMAIL= '" + email + "' or usrnme='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);

        return result;
    }

    public List<String[]> checkUserName(SignupRequestDTO signupRequestDTO) {
        List<String[]> result = null;
        String user = signupRequestDTO.getUserName();
        String mobile = signupRequestDTO.getPhoneNo();
        String email = signupRequestDTO.getEmail();
        String alias = "qtemp.usrprof";
        String file = "testdata.userprof(TRT)";
        String sql = "SELECT USRNME,UEMAIL,UPHONE FROM qtemp.usrprof where USRNME='" + user + "' or UEMAIL='" + email + "' or UPHONE = '" + mobile + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;
    }
}
