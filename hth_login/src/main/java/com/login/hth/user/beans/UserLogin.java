package com.login.hth.user.beans;

import com.login.hth.connection.iSeries;
import com.login.hth.user.dto.AppUserRole;
import com.login.hth.user.dto.UserDTO;
import com.login.hth.error.ErrorResponse;
import com.login.hth.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class UserLogin {
    @Autowired
    private JWTUtility jWTUtility;

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
        String sql = "SELECT USRNME,UPASS1,UEMAIL,UGROUP,USSN FROM QTEMP.USERPROF where USRNME='" + user + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

    public ResponseEntity<Object> checkUser(UserDTO user) {
        ErrorResponse er = new ErrorResponse();
        String[] result = null;
        result = getUserDetail(user.getName());
        if (result == null) {
            er.setStatus("User name not available.");
            er.setError(String.valueOf(400));
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        } else {
            if (result[1].trim().equals(user.getPassword())) {
                HashMap<String, String> data = new HashMap<>();
                data.put("name", user.getName().trim());
                data.put("email", result[2].trim());
                data.put("ssn", result[4].trim());
                data.put("group", result[3].trim());

                ArrayList<AppUserRole> roles = new ArrayList<>();
                roles.add(AppUserRole.ROLE_ADMIN);
                String token = jWTUtility.createToken(user.getName(), roles, data);

                HttpHeaders headers = new HttpHeaders();
                headers.set("token", token);
                return new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                er.setStatus("Password not matched.");
                er.setError(String.valueOf(400));
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
