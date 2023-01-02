package com.login.hth.user.beans;

import com.login.hth.common.MessageDTO;
import com.login.hth.connection.iSeries;
import com.login.hth.user.dto.UserVerifyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserVerify {
    public static List<String[]> getInsureData(String empIDPolcy) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSURE";
        String file = "TESTDATA.INSURE(TRT)";
        String sql = "SELECT IFNAM,ILNAM,IDOB FROM QTEMP.INSURE WHERE IEMPID ='" + empIDPolcy + "' or IPOLCY = '" + empIDPolcy + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

        public ResponseEntity<Object> checkValidation(UserVerifyDTO userValidationDTO){
            List<UserVerifyDTO> validationDTOList = new ArrayList<UserVerifyDTO>();
            List<String[]> insureList = UserVerify.getInsureData(userValidationDTO.getEmployPolicy());

        for(int i=0;i<insureList.size();i++) {
            String[] insure = insureList.get(i);
            if(!insure[0].trim().equals(userValidationDTO.getFirstName().trim())){
                System.out.println("FirstName: "+insure[0]);
                MessageDTO msg1 = new MessageDTO("First name did not match");
                return new ResponseEntity<>(msg1,HttpStatus.BAD_REQUEST);
            } else if(!insure[1].trim().equals(userValidationDTO.getLastName().trim())){
                MessageDTO msg2 = new MessageDTO("Last name did not match");
                return new ResponseEntity<>(msg2,HttpStatus.BAD_REQUEST);
            }else if(! insure[2].equals(userValidationDTO.getDateOfBirth())){
                MessageDTO msg3 = new MessageDTO("DOB did not match");
                return new ResponseEntity<>(msg3, HttpStatus.BAD_REQUEST);
            }else {
                MessageDTO messageDTO = new MessageDTO("Success");
                return ResponseEntity.ok(messageDTO);

            }
        }
        return ResponseEntity.accepted().body(userValidationDTO);
    }

}
