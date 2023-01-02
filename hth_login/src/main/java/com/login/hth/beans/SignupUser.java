package com.login.hth.beans;

import com.login.hth.security.iSeries;
import com.login.hth.dto.SignupRequestDTO;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignupUser {

    public List<String[]> insertUserDetails(SignupRequestDTO signupRequestDTO) {
        JSONObject obj = new JSONObject();
        obj.put("firstName", signupRequestDTO.getFirstName());
        obj.put("lastName", signupRequestDTO.getLastName());
        List<String[]> result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "INSERT INTO QTEMP.USERPROF(usrnme,uemail,uphone,upass1,usec1) values('" + signupRequestDTO.getUserName() + "'" +
                ",'" + signupRequestDTO.getEmail() + "','" + signupRequestDTO.getPhoneNo() + "','" + signupRequestDTO.getPassword() + "'" +
                ",'" + obj + "')";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;
    }

    public String[] checkUserName(String user) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME FROM QTEMP.USERPROF where USRNME='" + user + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

    public String[] getAllProfile() {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,USEC1 FROM QTEMP.USERPROF limit 1";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }
}
