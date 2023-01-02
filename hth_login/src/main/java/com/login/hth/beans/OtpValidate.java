package com.login.hth.beans;

import com.login.hth.dto.AppUserRole;
import com.login.hth.dto.ErrorResponseDTO;
import com.login.hth.dto.JWTTokenResponseDTO;
import com.login.hth.dto.OtpValidateDTO;
import com.login.hth.security.JWTUtility;
import com.login.hth.security.iSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private JWTUtility jWTUtility;
    @Autowired
    private UserLogin userLogin;

//    public static ResponseEntity<Object> updatePassword(OtpValidateDTO otpValidateDTO) {
//        List<String[]> result = null;
//        String alias = "QTEMP.USERPROF";
//        String file = "TESTDATA.USERPROF(TRT)";
//        String sql = "UPDATE QTEMP.USERPROF set UPASS1='" + otpValidateDTO.getPassword() + "' where USRNME='" + otpValidateDTO.getUserName() + "'";
//        result = iSeries.executeSQLByAlias(sql, alias, file);
//        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
//    }

    public void updateStatus(OtpValidateDTO otpValidateDTO, String time) {
        List<String[]> result = null;
        String alias = "QTEMP.USREML";
        String file = "TESTDATA.USREML(TRT)";
        String sql = "UPDATE qtemp.USREML set pustus=1 where pusrnme='" + otpValidateDTO.getUserName() + "'" +
                " and PDATE='" + removeZero(SendEmail.getCurrentDateAndTime()[0]) + "' and PTIME='" + time + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
    }

    public ResponseEntity<Object> otpValidate(OtpValidateDTO otpValidateDTO) {
        ErrorResponseDTO er = new ErrorResponseDTO();
        try {
            String[] result = null;
            String alias = "QTEMP.USREML";
            String file = "TESTDATA.USREML(TRT)";
            String sql = "SELECT * FROM QTEMP.USREML where PUSRNME='" + otpValidateDTO.getUserName() + "' and PEMAILK=" +
                    "'" + otpValidateDTO.getOtp() + "' and PDATE ='" + removeZero(SendEmail.getCurrentDateAndTime()[0]) + "' order by PTIME desc limit 1";
            System.out.println(sql);
            result = iSeries.executeSQLByAliasArray(sql, alias, file);
            if (result != null) {
                if (result[5].equals("1")) {
                    er.setMessage("Password updated Already");
                    return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
                } else {
                    long store = getTimeInMilliseconds(result[4]);
                    long current = getCurrentTimeInMilliSeconds();
                    long diff = current - store;
                    System.out.println(diff);
                    if (diff <= 900) {
                        String[] userData = userLogin.getUserDetailUser(otpValidateDTO.getUserName());
                        updateStatus(otpValidateDTO, result[4]);
                        HashMap<String, String> data = new HashMap<>();
                        data.put("name", otpValidateDTO.getUserName());
                        data.put("email", userData[2].trim());
                        data.put("ssn", userData[4].trim());
                        data.put("group", userData[3].trim());

                        ArrayList<AppUserRole> roles = new ArrayList<>();
                        roles.add(AppUserRole.ROLE_ADMIN);
                        String token = jWTUtility.createToken(result[0].trim(), roles, data);

                        // HttpHeaders headers = new HttpHeaders();
                        //headers.set("token", token);
                        JWTTokenResponseDTO dto = new JWTTokenResponseDTO();
                        dto.setToken(token);
                        return new ResponseEntity<>(dto, HttpStatus.OK);
                        // return updatePassword(otpValidateDTO);
                    } else {
                        er.setMessage("Bad Request");
                        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                er.setMessage("Bad Request");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        er.setMessage("Bad Request");
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


    //                SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
//                long time = sdf.parse(result[4]).getTime();
//                System.out.println(time);
//                long currentTime = System.currentTimeMillis();
//                System.out.println(currentTime);
    // String[] dateTime = SendEmail.getCurrentDateAndTime();
}
