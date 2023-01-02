package com.login.hth.beans;

import com.login.hth.connection.iSeries;
import com.login.hth.dto.ErrorResponseDTO;
import com.login.hth.dto.OtpValidateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class OtpValidate {

    public static ResponseEntity<Object> updatePassword(OtpValidateDTO otpValidateDTO) {
        List<String[]> result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "UPDATE QTEMP.USERPROF set UPASS1='" + otpValidateDTO.getPassword() + "' where USRNME='" + otpValidateDTO.getUserName() + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }

    public static void updateStatus(OtpValidateDTO otpValidateDTO, String time) {
        List<String[]> result = null;
        String alias = "QTEMP.USREML";
        String file = "TESTDATA.USREML(TRT)";
        String sql = "UPDATE qtemp.USREML set pustus=1 where pusrnme='" + otpValidateDTO.getUserName() + "'" +
                " and PDATE='" + removeZero(SendEmail.getCurrentDateAndTime()[0]) + "' and PTIME='" + time + "'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
    }

    public static ResponseEntity<Object> otpValidate(OtpValidateDTO otpValidateDTO) {
        ErrorResponseDTO er = new ErrorResponseDTO();
        try {
            String[] result = null;
            String alias = "QTEMP.USREML";
            String file = "TESTDATA.USREML(TRT)";
            String sql = "SELECT * FROM QTEMP.USREML where PUSRNME='" + otpValidateDTO.getUserName() + "' and PEMAILK=" +
                    "'" + otpValidateDTO.getOtp() + "' and PDATE ='" + removeZero(SendEmail.getCurrentDateAndTime()[0]) + "' order by PTIME desc limit 1";
            result = iSeries.executeSQLByAliasArray(sql, alias, file);
            if (result != null) {
                if(result[5].equals("1")){
                    er.setMessage("Password updated Already.");
                   // er.setError(String.valueOf(400));
                    return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
                }else {
                    long store = getTimeInMilliseconds(result[4]);
                    long current = getCurrentTimeInMilliSeconds();
                    long diff = current - store;
                    System.out.println(diff);
                    if (diff <= 900) {
                        updateStatus(otpValidateDTO, result[4]);
                        return updatePassword(otpValidateDTO);
                    } else {
                        er.setMessage("Link Expired.");
                        //er.setError(String.valueOf(400));
                        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                er.setMessage("Link expired.");
               // er.setError(String.valueOf(400));
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        er.setMessage("Error");
       // er.setError(String.valueOf(400));
        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }

    public static String removeZero(String date) {
        if (date.startsWith("0")) {
            date = date.replace("0", "");
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
