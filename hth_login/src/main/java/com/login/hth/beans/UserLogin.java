package com.login.hth.beans;

import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.UserDTO;
import com.login.hth.security.JWTUtility;
import com.login.hth.security.iSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLogin {
    @Autowired
    JWTUtility jWTUtility;
    public String[] getUserDetail(String email) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UPASS1,UEMAIL,UGROUP,USSN FROM QTEMP.USERPROF where UEMAIL='" + email + "' or USRNME='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

    public String[] getUserDetailUser(String user) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UPASS1,UEMAIL,UGROUP,USSN FROM QTEMP.USERPROF where USRNME='" + user + "' or UEMAIL='" + user + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

    public ResponseEntity<Object> checkUser(UserDTO user) {
        MessageDTO er = new MessageDTO();
        String[] result = null;
        result = getUserDetail(user.getEmail());
        if (result == null) {
            er.setMessage("Invalid Email/Password");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        } else {
            if (result[1].trim().equals(user.getPassword())) {
                er.setMessage("Success");
                return new ResponseEntity<>(er, HttpStatus.OK);
            } else {
                er.setMessage("Invalid Email/Password");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
