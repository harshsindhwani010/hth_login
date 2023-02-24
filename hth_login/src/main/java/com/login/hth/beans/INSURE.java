package com.login.hth.beans;

import com.login.hth.security.iSeries;

import java.util.List;

public class INSURE {
    private static String[] grpPlanList = null;

    public static List<String[]> getInsureData(String ssn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSURE";
        String file = "TESTDATA.INSURE(TRT)";
        String sql = "SELECT ILNAM,IFNAM,IGRPNO,IDIVNO,IADD1,IADD2,IINO01,IINO02,IINO03,IINO04,IINO05,IINO06,IINO07,IINO08,IINO09,IINO10,IINO11,IINO12,IINO13,IINO14,IINO15,IINO16,IINO17,IINO18,IINO19,IINO20,IINO21,IINO22,IINO23,IINO24,IINO25,IINO26,IINO27,IINO28,IINO29,IINO30,IINO31,IINO32,IINO33,IINO34,IINO35,IINO36,IINO37,IINO38,IINO39,IINO40,IINO41,IINO42,IINO43,IINO44,IINO45,IINO46,IINO47,IINO48,IINO49,IINO50,IINE01,IINE02,IINE03,IINE04,IINE05,IINE06,IINE07,IINE08,IINE09,IINE10,IINE11,IINE12,IINE13,IINE14,IINE15,IINE16,IINE17,IINE18,IINE19,IINE20,IINE21,IINE22,IINE23,IINE24,IINE25,IINE26,IINE27,IINE28,IINE29,IINE30,IINE31,IINE32,IINE33,IINE34,IINE35,IINE36,IINE37,IINE38,IINE39,IINE40,IINE41,IINE42,IINE43,IINE44,IINE45,IINE46,IINE47,IINE48,IINE49,IINE50  FROM QTEMP.INSURE WHERE ISSN = '" + ssn + "'";

        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getgrpmstData(String grpno) {
        List <String[]> resultList = null;
        String alias = "QTEMP.GRPMST";
        String file = "TESTDATA.GRPMST(TRT)";
        String sql = "SELECT * FROM QTEMP.GRPMSTN INNER JOIN QTEMP.BLCKPLN ON GRPMST.GCARR = BLCKPLN.CPNBR AND GRPMST.GPL1 = BLCKPLN.CPPLN" ;
        resultList = iSeries.executeSQLByAlias(sql, alias, file);

        return resultList;
    }

    public static String[] getblckplnData(String cpnumber) {
        String[] resultList = null;
        String alias = "QTEMP.BLCKPLN";
        String file = "TESTDATA.BLCKPLN(TRT)";
        String sql = "SELECT * FROM QTEMP.BLCKPLN WHERE CPNBR =" +cpnumber+"'";
        resultList = iSeries.executeSQLByAliasArray(sql, alias, file);
        return resultList;
    }
    public static List<String[]> getInshstData(String hssn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSHST";
        String file = "TESTDATA.INSHST(TRT)";
        String sql = "SELECT * FROM QTEMP.INSHST WHERE HSSN =" +hssn+"'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }
    public static List<String[]> getDependentData(String dessn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSDEP";
        String file = "TESTDATA.INSDEP(TRT)";
        String sql = "SELECT DLNAM,DFNAM,DCODE,DSEX,DDOB,DEFF FROM QTEMP.INSDEP WHERE DESSN =" +dessn+"'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }
}
