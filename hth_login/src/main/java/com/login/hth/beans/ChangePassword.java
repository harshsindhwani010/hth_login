package com.login.hth.beans;

import com.login.hth.dto.ErrorResponseDTO;
import com.login.hth.security.iSeries;
import org.springframework.stereotype.Component;

@Component
public class ChangePassword {

    public String checkUser(String email){
        ErrorResponseDTO er = new ErrorResponseDTO();
        String[] result = null;
        String alias = "QTEMP.USERPROF";
        String file = "TESTDATA.USERPROF(TRT)";
        String sql = "SELECT USRNME,UGROUP,UEMAIL FROM QTEMP.USERPROF where UEMAIL='" + email + "'";
        result = iSeries.executeSQLByAliasArray(sql, alias, file);
        if (result != null) {
           return "Success";
        } else {
            return "fail";
        }
    }
}
