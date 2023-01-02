package com.login.hth.beans;

import com.login.hth.connection.iSeries;
import com.login.hth.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SendEmail {
    public static ResponseEntity<Object> checkUser(String email){
        ErrorResponseDTO er = new ErrorResponseDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UGROUP,UEMAIL FROM QTEMP.USERPROF where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result != null) {
            return insertOtp(result);
        } else {
            er.setMessage("Password not matched.");
           // er.setError(String.valueOf(400));
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> insertOtp(String[] user) {
        String randomNumber = getRandomNumberString();
        String[] dateTime = getCurrentDateAndTime();
        List<String[]> result = null;
        String alias = "QTEMP.USREML";
        String file = "TESTDATA.USREML(TRT)";
        String sql = "INSERT INTO QTEMP.USREML(PGROUP,PUSRNME,PEMAILK,PDATE,PTIME,PUSTUS) " +
                "VALUES ('" + user[1].trim() + "','" + user[0].trim() + "','" + randomNumber + "','"+dateTime[0]+"','"+dateTime[1]+"','0')";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return new ResponseEntity<>("Otp sent successfully", HttpStatus.OK);
    }

    public static String getRandomNumberString() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String[] getCurrentDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy HHmmss");
        Date date = new Date();
        String[] dateTime = formatter.format(date).split(" ");
        return dateTime;
    }
}
