package com.login.hth.user.beans;

import com.login.hth.common.MessageDTO;
import com.login.hth.connection.iSeries;
import com.login.hth.user.dto.CreateAccountDTO;
import com.login.hth.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CreateAccount {
    @Autowired
    JWTUtility jwtUtility;

    public static ResponseEntity<Object> createUser(CreateAccountDTO userDTO){
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            String alias = "QTEMP.USERPROF";
            String file = "TESTDATA.USERPROF(TRT)";
            String sql = "INSERT INTO QTEMP.USERPROF(USRNME,UPHONE,UEMAIL,UPASS1) " +
                    "VALUES ('" + userDTO.getUserName() + "','" + userDTO.getPhoneNumber() + "','" + userDTO.getEmailAddress() + "','" + userDTO.getPassword() + "')";
            iSeries.executeSQLByAlias(sql, alias, file);

            MessageDTO msg = new MessageDTO("Account created successfully");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }else {
            MessageDTO msg1 = new MessageDTO("Password Not Matched");
            return new ResponseEntity<>(msg1, HttpStatus.BAD_REQUEST);
        }
    }

}
