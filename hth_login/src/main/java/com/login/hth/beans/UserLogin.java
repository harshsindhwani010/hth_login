package com.login.hth.beans;

import com.login.hth.connection.iSeries;
import com.login.hth.dto.AppUserRole;
import com.login.hth.dto.UserDTO;
import com.login.hth.dto.ErrorResponseDTO;
import com.login.hth.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class UserLogin {
    @Autowired
    private JWTUtility jWTUtility;

    public String[] getUserDetail(String user) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UPASS1,UEMAIL,UGROUP,USSN FROM QTEMP.USERPROF where USRNME='" + user + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

    public ResponseEntity<Object> checkUser(UserDTO user) {
        ErrorResponseDTO er = new ErrorResponseDTO();
        String[] result = null;
        result = getUserDetail(user.getName());
        if (result == null) {
            er.setMessage("User name not available.");
         //   er.setError(String.valueOf(400));
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
                er.setMessage("Password not matched.");
                //er.setError(String.valueOf(400));
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
