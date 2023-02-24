package com.login.hth.beans;

import com.login.hth.security.iSeries;

import java.util.List;

public class CoverageDetails {
    public static List<String[]> coverageProfile(String ssn) {
        List<String[]> result = null;
        String[] aliases = {"QTEMP.INSURE"};
        String[] files = {"TESTDATA.INSURE(TRT)"};
        String sql = "SELECT IFNAM,ILNAM,IGRPNO,IADD1,IADD2,IINO01,IINO02,IINO03,IINO04, IINO05,IINO06,IINO07,IINO08,IINO09,IINO10,IINO11,IINO12,"
                + "IINO13,IINO14,IINO15,IINO16,IINO17,IINO18,IINO19,IINO20,IINO21,IINO22,IINO23,IINO24,IINO25,IINO26,IINO27,IINO28,"
                + "IINO29,IINO30,IINO31,IINO32,IINO33,IINO34,IINO35,IINO36,IINO37,IINO38,IINO39,IINO40,IINO41,IINO42,IINO43,IINO44,"
                + "IINO45,IINO46,IINO47,IINO48,IINO49,IINO50,IINE01,IINE02,IINE03,IINE04,IINE05,IINE06,IINE07,IINE08,IINE09,IINE10,"
                + "IINE11,IINE12,IINE13,IINE14,IINE15,IINE16,IINE17,IINE18,IINE19,IINE20,IINE21,IINE22,IINE23,IINE24,IINE25,IINE26,"
                + "IINE27,IINE28,IINE29,IINE30,IINE31,IINE32,IINE33,IINE34,IINE35,IINE36,IINE37,IINE38,IINE39,IINE40,IINE41,IINE42,"
                + "IINE43,IINE44,IINE45,IINE46,IINE47,IINE48,IINE49,IINE50  FROM QTEMP.INSURE WHERE ISSN='" + ssn + "'";
        result = iSeries.executeSQLByAlias(sql, aliases, files);
        if (result.size() == 0) {
            return null;
        } else {
            return result;

        }
    }

    public static List<String[]> coveragePlanCode(String grpno) {
        List<String[]> result = null;
        String[] aliases = {"QTEMP.GRPMST"};
        String[] files = {"TESTDATA.GRPMST(TRT)"};
        String sql = "SELECT GPL1,GPL2,GPL3,GPL4,GPL5,GPL6,GPL7,GPL8,GPL9,GPL10,GPL11,GPL12,GPL13,GPL14,GPL15,"
                + "GPL16,GPL17,GPL18,GPL19,GPL20,GPL21,GPL22,GPL23,GPL24,GPL25,GPL26,GPL27,GPL28,GPL29,GPL30,"
                + "GPL31,GPL32,GPL33,GPL34,GPL35,GPL36,GPL37,GPL38,GPL39,GPL40,GPL41,GPL42,GPL43,GPL44,GPL45,"
                + "GPL46,GPL47,GPL48,GPL49,GPL50 FROM QTEMP.GRPMST WHERE GGRPNO='" + grpno+ "'";
        result = iSeries.executeSQLByAlias(sql, aliases, files);
        if (result.size() == 0) {
            return null;
        } else {
            return result;

        }
    }

    public static String[] dependedDetail(String grpno) {
        String[] resultList = null;
        String alias = "QTEMP.BLCKPLN";
        String file = "TESTDATA.BLCKPLN(TRT)";
        String sql = "SELECT * FROM QTEMP.BLCKPLN WHERE ISSN='" + grpno + "'";
        resultList = iSeries.executeSQLByAliasArray(sql, alias, file);
        return resultList;

        }
    }


