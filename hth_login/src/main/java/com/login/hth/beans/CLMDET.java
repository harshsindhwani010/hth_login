package com.login.hth.beans;

import com.login.hth.security.iSeries;

import java.util.List;

public class CLMDET {
    public static List<String[]> getHeaderData(String ssn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.CLMHDR";
        String file = "TESTDATA.CLMHDR(TRT)";
        String sql = "SELECT HCLMNO,HTOTCL,HFILL7,HTOTCK,HDEP FROM QTEMP.CLMHDR WHERE HSSN ='" + ssn + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getDetailData(String claimNum) {
       List<String[]>resultList = null;
        String alias = "QTEMP.CLMDET";
        String file = "TESTDATA.CLMDET(TRT)";
        String sql = "SELECT DPROVD,DDOS,DCOPAY,DAMTAL,DAMTEX,DAMTDD FROM QTEMP.CLMDET WHERE DCLMNO ='" + claimNum + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getInsureData(String issn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSURE";
        String file = "TESTDATA.INSURE(TRT)";
        String sql = "SELECT IFNAM,ILNAM FROM QTEMP.INSURE WHERE ISSN ='" + issn + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }


}
