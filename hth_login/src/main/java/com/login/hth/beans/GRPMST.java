package com.login.hth.beans;

import com.login.hth.connection.iSeries;

import java.util.List;

public class GRPMST {
    public static boolean groupExists(String group) {
        String alias = "QTEMP.GRPMST";
        String file = "TESTDATA.GRPMST(TRT)";
        String sql = "SELECT GGRPNO, GVIPST, GGRPNM, GCARR, GSTAT FROM QTEMP.GRPMST where GGRPNO='" + group + "'";
        List<String[]> resultList = iSeries.executeSQLByAlias(sql, alias, file);
        if(resultList.size()>0){
          return true;
        }else{
          return false;
        }
    }
}
