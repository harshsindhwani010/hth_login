package com.login.hth.beans;

import com.login.hth.security.iSeries;
import java.util.List;


public class INSURE {

    private static String[] grpPlanList = null;

    public static List<String[]> getInsureData(String ssn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSURE";
        String file = "TESTDATA.INSURE(TRT)";
        String sql = "SELECT ILNAM,IFNAM,IGRPNO,IDIVNO,IDOB,IADD1,IADD2,IINO01,IINO02,IINO03,IINO04,IINO05,IINO06,IINO07,IINO08,IINO09,IINO10,IINO11,IINO12,IINO13,IINO14,IINO15,IINO16,IINO17,IINO18,IINO19,IINO20,IINO21,IINO22,IINO23,IINO24,IINO25,IINO26,IINO27,IINO28,IINO29,IINO30,IINO31,IINO32,IINO33,IINO34,IINO35,IINO36,IINO37,IINO38,IINO39,IINO40,IINO41,IINO42,IINO43,IINO44,IINO45,IINO46,IINO47,IINO48,IINO49,IINO50,IINE01,IINE02,IINE03,IINE04,IINE05,IINE06,IINE07,IINE08,IINE09,IINE10,IINE11,IINE12,IINE13,IINE14,IINE15,IINE16,IINE17,IINE18,IINE19,IINE20,IINE21,IINE22,IINE23,IINE24,IINE25,IINE26,IINE27,IINE28,IINE29,IINE30,IINE31,IINE32,IINE33,IINE34,IINE35,IINE36,IINE37,IINE38,IINE39,IINE40,IINE41,IINE42,IINE43,IINE44,IINE45,IINE46,IINE47,IINE48,IINE49,IINE50,IINT01,IINT02,IINT03,IINT04,IINT05,IINT06,IINT07,IINT08,IINT09,IINT10,IINT11,IINT12,IINT13,IINT14,IINT15,IINT16,IINT17,IINT18,IINT19,IINT20,IINT21,IINT22,IINT23,IINT24,IINT25,IINT26,IINT27,IINT28,IINT29,IINT30,IINT31,IINT32,IINT33,IINT34,IINT35,IINT36,IINT37,IINT38,IINT39,IINT40,IINT41,IINT42,IINT43,IINT44,IINT45,IINT46,IINT47,IINT48,IINT49,IINT50  FROM QTEMP.INSURE WHERE ISSN = '" + ssn + "'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getGroupMasterData(String grpno) {
        List<String[]> resultList = null;
        String alias = "QTEMP.GRPMST";
        String file = "TESTDATA.GRPMST(TRT)";
        String sql = "SELECT GCARR,GGRPNM,GPL1,GPL2,GPL3,GPL4,GPL5,GPL6,GPL7,GPL8,GPL9,GPL10,GPL11,GPL12,GPL13,GPL14,GPL15,GPL16,GPL17,GPL18,GPL19,GPL20,GPL21,GPL22,GPL23,GPL24,GPL25,GPL26,GPL27,GPL28,GPL29,GPL30,GPL31,GPL32,GPL33,GPL34,GPL35,GPL36,GPL37,GPL38,GPL39,GPL40,GPL41,GPL42,GPL43,GPL44,GPL45,GPL46,GPL47,GPL48,GPL49,GPL50 FROM QTEMP.GRPMST WHERE GGRPNO = '" + grpno + "'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getBlckplnData(String cpnumber, String cppln, String plan) {
        List<String[]> resultList = null;
        String[] aliases = {"QTEMP.GRPMST", "QTEMP.BLCKPLN"};
        String[] files = {"TESTDATA.GRPMST(TRT)", "TESTDATA.BLCKPLN(TRT)"};
        String sql = "SELECT CCOVRG FROM QTEMP.BLCKPLN A JOIN QTEMP.GRPMST B ON B.GCARR = A.CPNBR AND B.GPL1 = A.CPPLN WHERE CPNBR = '" + cpnumber.trim() + "' AND CPPLN = '" + cppln.trim() + "'";
        resultList = iSeries.executeSQLByAlias(sql, aliases, files);
        return resultList;
    }

    public static List<String[]> getInshstData(String hssn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSHST";
        String file = "TESTDATA.INSHST(TRT)";
        String sql = "SELECT HGRPNO,HPLN,HPEFF,HPTER FROM QTEMP.INSHST WHERE HSSN = '" + hssn + "'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getPlanDes(String plan) {
        List<String[]> resultList = null;
        String alias = "Qtemp.BLCKPLN";
        String file = "TESTDATA.BLCKPLN(TRT)";
        String sql = "SELECT CPLDS FROM Qtemp.BLCKPLN WHERE CPPLN = '" + plan.trim() + "'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getDependentData(String dessn) {
        List<String[]> resultList = null;
        String alias = "QTEMP.INSDEP";
        String file = "TESTDATA.INSDEP(TRT)";
        String sql = "SELECT DLNAM,DFNAM,DCODE,DSEX,DDOB,DEFF FROM QTEMP.INSDEP WHERE DESSN = '" + dessn + "'";
        resultList = iSeries.executeSQLByAlias(sql, alias, file);
        return resultList;
    }

    public static List<String[]> getClmHdr(String ssn) {
        String alias = "QTEMP.CLMHDR";
        String file = "TESTDATA.CLMHDR(TRT)";
        String sql = "select * from qtemp.CLMHDR where HSSN= '" + ssn + "'";
        List<String[]> resultData = iSeries.executeSQLByAlias(sql, alias, file);
        return resultData;
    }

    public static List<String[]> getClmdet(String hclmno) {
        String alias = "QTEMP.clmdet";
        String file = "TESTDATA.clmdet(TRT)";
        String sql = "select * from qtemp.clmdet where dclmno = '" + hclmno + "'";
        List<String[]> resultData = iSeries.executeSQLByAlias(sql, alias, file);
        return resultData;
    }

    public static List<String[]> getClmDates(List dates, String ssn) {

        String conditions= "1=1";
        StringBuilder sb = new StringBuilder();

        if(dates.size()>0) {

            sb.append("(");
            for (int i = 0; i < dates.size(); i++) {
                sb.append("'" + dates.get(i) + "'");
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            conditions = conditions + " AND HDOS IN "+sb.toString();
        }
        String alias = "QTEMP.CLMHDR";
        String file = "TESTDATA.CLMHDR(TRT)";
        String sql = "SELECT HDOS FROM QTEMP.CLMHDR where " + conditions + " and HSSN='" + ssn + "'";
        System.out.println(sql);
        List<String[]> resultData = iSeries.executeSQLByAlias(sql, alias, file);
        return resultData;
    }
    public static List<String[]> getClmData( String ssn) {
        String alias = "QTEMP.CLMHDR";
        String file = "TESTDATA.CLMHDR(TRT)";
        String sql = "SELECT * FROM QTEMP.CLMHDR WHERE HSSN = '" + ssn + "'";
        System.out.println(sql);
        List<String[]> resultData = iSeries.executeSQLByAlias(sql, alias, file);
        return resultData;
    }

}