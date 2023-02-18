package com.login.hth.beans;

import com.login.hth.dto.ChangePasswordDTO;
import com.login.hth.dto.MessageDTO;
import com.login.hth.security.iSeries;
import com.login.hth.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class SendEmail {

    @Autowired
    IEmailService iEmailService;
    public ResponseEntity<Object> updatePassword(String password, String email) {
        MessageDTO er = new MessageDTO();
        List<String[]> result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "UPDATE QTEMP.USERPROF set UPASS1 = '" + password + "' where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        if (result != null) {
            er.setMessage("Password Updated");
            return new ResponseEntity<>(er, HttpStatus.OK);
        } else {
            er.setMessage("Bad Request.");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> changePassword(ChangePasswordDTO changePasswordDTO, String email) {
        MessageDTO er = new MessageDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT UPASS1 FROM QTEMP.USERPROF where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result != null && result.length > 0) {
            if (!result[0].trim().equals(changePasswordDTO.getCurrentPassword())) {
                er.setMessage("Current Password not matched");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            } else {
                return updatePassword(changePasswordDTO.getNewPassword(), email);
            }
        } else {
            er.setMessage("Bad Request");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> checkUser(String email) {
        MessageDTO er = new MessageDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UGROUP,UEMAIL FROM QTEMP.USERPROF where UEMAIL='" + email + "' OR USRNME= '" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result != null) {
            return insertOtp(result);

        } else {
            er.setMessage("Password not matched.");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> insertOtp(String[] user) {
        MessageDTO dto = new MessageDTO();

        try {
            String randomNumber = getRandomNumberString();
            String content = "This is your otp for validate " + randomNumber;
            String[] dateTime = getCurrentDateAndTime();
            List<String[]> result = null;
            String alias = "QTEMP.USREML";
            String file = "TESTDATA.USREML(TRT)";
            String sql = "INSERT INTO QTEMP.USREML(PGROUP,PUSRNME,PEMAILK,PDATE,PTIME,PUSTUS) " +
                    "VALUES ('" + user[1].trim() + "','" + user[0].trim() + "','" + randomNumber + "','" + dateTime[0] + "','" + dateTime[1] + "','0')";
            result = iSeries.executeSQLByAlias(sql, alias, file);
            System.out.println("random number:" + randomNumber);
            String[] to = new String[1];
            to[0] = user[2].trim();
            String subject = "Otp Validation";
            iEmailService.sendSimpleMail(to[0],subject,content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dto.setMessage("OTP Sent");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public String getRandomNumberString() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String[] getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy HHmmss");
        Date date = new Date();
        String[] dateTime = formatter.format(date).split(" ");
        return dateTime;
    }


}
