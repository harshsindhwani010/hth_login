package com.login.hth.beans;

import com.login.hth.security.iSeries;
import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.UserVerifyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserVerify {
    public String[] getInsureData(String empIDPolcy) {
        String[] resultList = null;
        String alias = "QTEMP.INSURE";
        String file = "TESTDATA.INSURE(TRT)";
        String sql = "SELECT IFNAM,ILNAM,IDOB,ISSN FROM QTEMP.INSURE WHERE IEMPID ='" + empIDPolcy + "' or IPOLCY = '" + empIDPolcy + "'";

        resultList = iSeries.executeSQLByAliasArray(sql, alias, file);
        return resultList;
    }

    public ResponseEntity<Object> checkValidation(UserVerifyDTO userValidationDTO) {
        MessageDTO messageDto = new MessageDTO();
        String[] insure = getInsureData(userValidationDTO.getEmployPolicy());
        if (insure == null) {
            messageDto.setMessage("User not found");
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        } else {
            if (!insure[0].trim().equals(userValidationDTO.getFirstName().trim())) {
                messageDto.setMessage("First name did't match");
                return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
            } else if (!insure[1].trim().equals(userValidationDTO.getLastName().trim())) {
                messageDto.setMessage("Last name did not match");
                return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
            } else if (!insure[2].trim().equals(userValidationDTO.getDateOfBirth())) {
                messageDto.setMessage("DOB did not match");
                return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
            } else {
                if (getUserProfile(insure[3].trim()).length > 0) {
                    messageDto.setMessage("User Already Registered");
                    return new ResponseEntity<>(messageDto, HttpStatus.OK);
                } else {
                    messageDto.setMessage("Success");
                    return new ResponseEntity<>(messageDto, HttpStatus.OK);
                }
            }
        }
    }

    public String[] getUserProfile(String ssn) {
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT * FROM QTEMP.USERPROF where USSN='" + ssn + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        return result;
    }

}
