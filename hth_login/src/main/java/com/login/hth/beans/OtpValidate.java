package com.login.hth.beans;

import com.login.hth.dto.*;
import com.login.hth.security.JWTUtility;
import com.login.hth.security.iSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class OtpValidate {

    @Autowired
    JWTUtility jWTUtility;
    @Autowired
    UserLogin userLogin;


    public void updateStatus(OtpValidateDTO otpValidateDTO, String time) {
        List<String[]> result = null;
        String alias = "QTEMP.USREML";
        String file = "TESTDATA.USREML(TRT)";
        String sql = "UPDATE qtemp.USREML set pustus=1 where pusrnme='" + otpValidateDTO.getUserName() + "'" +
                " and PDATE='" + removeZero(SendEmail.getCurrentDateAndTime()[0]) + "' and PTIME='" + time + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
    }

    public String[] getUsername(String email) {
        MessageDTO er = new MessageDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UGROUP,UEMAIL FROM QTEMP.USERPROF where UEMAIL='" + email + "' OR USRNME= '" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result != null) {
            return result;
        } else {
            er.setMessage("Password not matched.");
            return result;
        }
    }

    public ResponseEntity<Object> otpValidate(OtpValidateDTO otpValidateDTO) {
        MessageDTO er = new MessageDTO();
        try {
            String[] result = null;
            String[] userData = getUsername(otpValidateDTO.getUserName());
            String alias = "QTEMP.USREML";
            String file = "TESTDATA.USREML(TRT)";
            String sql = "SELECT * FROM QTEMP.USREML where PUSRNME='" + userData[0].trim() + "' and PEMAILK=" +
                    "'" + otpValidateDTO.getOtp() + "' and PDATE ='" + removeZero(SendEmail.getCurrentDateAndTime()[0]) + "' order by PTIME desc limit 1";
            result = iSeries.executeSQLByAliasArray(sql, alias, file);
            if (result != null) {
                if (result[5].equals("1")) {
                    er.setMessage("Invalid OTP");
                    return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
                } else {
                    String time=result[4];
                    if(time.length()==5)
                        time="0"+time;
                    long store = getTimeInMilliseconds(time);
                    long current = getCurrentTimeInMilliSeconds();
                    long diff = current - store;
                    if (diff <= 900) {
                        String[] userData1 = userLogin.getUserDetailUser(otpValidateDTO.getUserName());
                        updateStatus(otpValidateDTO, result[4]);
                        HashMap<String, String> data = new HashMap<>();
                        data.put("name", otpValidateDTO.getUserName());
                        data.put("email", userData1[2].trim());
                        data.put("ssn", userData1[4].trim());
                        data.put("group", userData1[3].trim());

                        ArrayList<AppUserRole> roles = new ArrayList<>();
                        roles.add(AppUserRole.ROLE_ADMIN);
                        String token = jWTUtility.createToken(otpValidateDTO.getUserName(), roles, data);

                        JWTTokenResponseDTO dto = new JWTTokenResponseDTO();
                        dto.setToken(token);
                        return new ResponseEntity<>(dto, HttpStatus.OK);
                    } else {
                        er.setMessage("OTP Expired");
                        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                er.setMessage("Invalid OTP");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            er.setMessage("Exception " + ex.getMessage());
        }
        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }

    public static String removeZero(String date) {
        if (date.startsWith("0")) {
            date = date.replaceFirst("0", "");
        }
        return date;
    }

    public static int getCurrentTimeInMilliSeconds() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        LocalTime localTime = LocalTime.parse(formatter.format(date).toString());
        int millis = localTime.toSecondOfDay();

        return millis;
    }

    public static int getTimeInMilliseconds(String time) {
        int millis = 0;
        try {
            Date d = new SimpleDateFormat("HHmmss").parse(time);
            SimpleDateFormat d2 = new SimpleDateFormat("HH:mm:ss");
            String formattedProcessDate = d2.format(d).toString();
            LocalTime localTime = LocalTime.parse(formattedProcessDate);
            millis = localTime.toSecondOfDay();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return millis;
    }
}
