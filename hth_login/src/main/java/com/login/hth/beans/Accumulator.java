package com.login.hth.beans;

import com.login.hth.security.iSeries;

import java.util.List;

public class Accumulator {
    public static List<String[]> accumulatorData(String group) {
        List<String[]> resultList = null;
        String alias = "QTEMP.MEDMAX";
        String file = "TESTDATA.MEDMAX(TRT)";
        String sql = "SELECT  MPLAN, MTYPES,MANLMX,MPCTPY, MCOPAY,MOOPOV FROM QTEMP.MEDMAX WHERE MGRPNO. ='" + group + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

}
