package com.login.hth.beans;

import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.UserVerifyDTO;
import com.login.hth.security.iSeries;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserVerify {
    public String[] getInsureData(String empIDPolcy) {
        String[] resultList = null;
        String alias = "QTEMP.INSURE";
        String file = "TESTDATA.INSURE(TRT)";
        String sql = "SELECT IFNAM,ILNAM,IDOB,ISSN FROM QTEMP.INSURE WHERE IEMPID ='" + empIDPolcy + "' or IPOLCY = '" + empIDPolcy + "' or issn='" + empIDPolcy + "'";

        resultList = iSeries.executeSQLByAliasArray(sql, alias, file);
        return resultList;
    }

    public ResponseEntity<Object> checkValidation(UserVerifyDTO userValidationDTO) {
        MessageDTO messageDto = new MessageDTO();
        String[] insure = getInsureData(userValidationDTO.getEmployPolicy());
        if (insure == null) {
            messageDto.setMessage("User Not Found 1");
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        } else {
            if (!insure[0].trim().equals(userValidationDTO.getFirstName().trim())) {
                messageDto.setMessage("User Not Found 2");
                return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
            } else if (!insure[1].trim().equals(userValidationDTO.getLastName().trim())) {
                messageDto.setMessage("User Not Found 3");
                return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
            } else if (!insure[2].trim().equals(userValidationDTO.getDateOfBirth())) {
                // userValidationDTO.setDateOfBirth();
                messageDto.setMessage("User Not Found 4");
                return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
            } else {
                if (getUserProfile(insure[3].trim()) != null && getUserProfile(insure[3].trim()).length > 0) {
                    messageDto.setMessage("User Already Registered");
                    return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
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
