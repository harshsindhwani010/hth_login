package com.login.hth.beans;

import com.login.hth.security.iSeries;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfile {

    public static List<String[]> getuserProfile(String ssn) {
        List<String[]> result = null;
        String[] aliases = {"QTEMP.INSURE", "QTEMP.USERPROF"};
        String[] files = {"TESTDATA.INSURE(TRT)", "TESTDATA.USERPROF(TRT)"};
        String sql = "SELECT IFNAM,ILNAM,IDOB,UPHONE,USRNME,UEMAIL,IPOLCY,IEMPID FROM QTEMP.INSURE JOIN qtemp.userprof on ISSN=USSN  WHERE ISSN='"+ssn+"'";
        result = iSeries.executeSQLByAlias(sql, aliases, files);
        if (result.size() == 0) {
            return null;
        } else {
            return result;

        }

    }

}

