package com.login.hth.beans;

import com.login.hth.dto.ChangePasswordDTO;
import com.login.hth.dto.MessageDTO;
import com.login.hth.security.iSeries;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class SendEmail {
    public ResponseEntity<Object> updatePassword(String password, String email){
        MessageDTO er = new MessageDTO();
        List<String[]> result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "UPDATE QTEMP.USERPROF set UPASS1 = '"+password+"' where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        if (result != null) {
            er.setMessage("Password Updated");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }else{
            er.setMessage("Bad Request.");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> changePassword(ChangePasswordDTO changePasswordDTO, String email){
        MessageDTO er = new MessageDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT PASS1 QTEMP.USERPROF where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result.length>0) {
            if(!result[0].equals(changePasswordDTO.getCurrentPassword())){
                er.setMessage("Current Password not matched");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }else {
                er.setMessage("Password Updated");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        }else{
            er.setMessage("Bad Request");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> checkUser(String email){
        MessageDTO er = new MessageDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UGROUP,UEMAIL FROM QTEMP.USERPROF where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result != null) {
            return insertOtp(result);
        } else {
            er.setMessage("Password not matched.");
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
        System.out.println("random number:"+randomNumber);
        return new ResponseEntity<>("OTP Sent to your Email. Please Check your Inbox", HttpStatus.OK);
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
