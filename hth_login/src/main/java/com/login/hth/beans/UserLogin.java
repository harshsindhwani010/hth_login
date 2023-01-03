package com.login.hth.beans;

import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.UserDTO;
import com.login.hth.security.JWTUtility;
import com.login.hth.security.iSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLogin {
    @Autowired
    JWTUtility jWTUtility;
    public static String config(String usrID) {
        String sql = "SELECT USRMBR FROM HTHDATV1.SYSUSRP WHERE USRID='" + usrID.toUpperCase() + "'";
        List<String[]> resultList = iSeries.executeSQL(sql);
        if (resultList.isEmpty()) {
            return "   ";
        } else {
            return resultList.get(0)[0].trim();
        }
    }
    public String[] getUserDetail(String user) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UPASS1,UEMAIL,UGROUP,USSN FROM QTEMP.USERPROF where UEMAIL='" + user + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

    public String[] getUserDetailUser(String user) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UPASS1,UEMAIL,UGROUP,USSN FROM QTEMP.USERPROF where USRNME='" + user + "'";
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
//                HashMap<String, String> data = new HashMap<>();
//                data.put("name", result[0].trim());
//                data.put("email", result[2].trim());
//                data.put("ssn", result[4].trim());
//                data.put("group", result[3].trim());
//
//                ArrayList<AppUserRole> roles = new ArrayList<>();
//                roles.add(AppUserRole.ROLE_ADMIN);
//                String token = jWTUtility.createToken(result[0].trim(), roles, data);
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("token", token);
                //ErrorResponse er = new ErrorResponse();
                er.setMessage("Success");
                return new ResponseEntity<>(er, HttpStatus.OK);
            } else {
                er.setMessage("Invalid Email/Password");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
