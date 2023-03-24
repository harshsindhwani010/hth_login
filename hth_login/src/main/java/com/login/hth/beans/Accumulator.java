package com.login.hth.beans;

import com.login.hth.security.iSeries;

import java.util.List;

public class Accumulator {
    public static List<String[]> accumulatorData(String ssn) {
        List<String[]> resultList = null;
        String[] alias ={"QTEMP.MEDMAX", "QTEMP.MEDACC"};
        String[] files = {"TESTDATA.MEDMAX(RH1)", "TESTDATA.MEDACC(RH1)"};
        String sql = "SELECT  MPLAN, MANLMX, ADEDMT, FDIMET, APLNPR, AANNDE FROM QTEMP.MEDMAX JOIN QTEMP.MEDACC ON AGRPNO= MGRPNO WHERE ASSN ='" + ssn + "'";
        System.out.println(sql);
        resultList = iSeries.executeSQLByAlias(sql, alias, files);
        return resultList;
    }

}
