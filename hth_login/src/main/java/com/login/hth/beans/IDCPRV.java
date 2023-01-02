package com.login.hth.beans;

import com.login.hth.security.iSeries;
import com.login.hth.dto.IdCardResponseDTO;

public class IDCPRV {

    public static IdCardResponseDTO generateIDCARD(String[] grpList, String[] ssnList, String device) {
        IdCardResponseDTO response = new IdCardResponseDTO();
        String[] frontLine = new String[18];
        String[] backLine = new String[18];
        String[] backModifier = new String[18];
        String[] frontModifier = new String[18];
        String[] frontLogo = new String[9];
        String[] backLogo = new String[9];
        String member = "BI1";
        String alias = "QTEMP.IDCPRV";
        String file = "DFLIB.IDCPRV(" + member + ")";
        String sql;
        String[] resultList;

        StringBuilder whereClause = new StringBuilder("(");
        whereClause.append("(PGRP='").append(grpList[0]).append("' AND PSSN='").append(ssnList[0]).append("')");
        for (int idx = 1; idx < grpList.length; idx++) {
            whereClause.append(" OR (PGRP='").append(grpList[idx]).append("' AND PSSN='").append(ssnList[idx]).append("')");
        }
        whereClause.append(")");
        sql = "SELECT IGRP, IDIV, ICRD#, PLARF, PLARB, " // {idx 0 ~ 4}
                + "PFL01, PFL02, PFL03, PFL04, PFL05, PFL06, PFL07, PFL08, PFL09, PFL10, PFL11, PFL12, PFL13, PFL14, PFL15, PFL16, PFL17, PFL18, " // {idx 5 ~ 22}
                + "PBL01, PBL02, PBL03, PBL04, PBL05, PBL06, PBL07, PBL08, PBL09, PBL10, PBL11, PBL12, PBL13, PBL14, PBL15, PBL16, PBL17, PBL18, " // {idx 23 ~ 40}
                + "PFM01, PFM02, PFM03, PFM04, PFM05, PFM06, PFM07, PFM08, PFM09, PFM10, PFM11, PFM12, PFM13, PFM14, PFM15, PFM16, PFM17, PFM18, " // {idx 41 ~ 58}
                + "PBM01, PBM02, PBM03, PBM04, PBM05, PBM06, PBM07, PBM08, PBM09, PBM10, PBM11, PBM12, PBM13, PBM14, PBM15, PBM16, PBM17, PBM18, " // {idx 59 ~ 76}
                + "PFLG1, PFLG2, PFLG3, PFLG4, PFLG5, PFLG6, PFLG7, PFLG8, PFLG9, " // {idx 77 ~ end}
                + "PBLG1, PBLG2, PBLG3, PBLG4, PBLG5, PBLG6, PBLG7, PBLG8, PBLG9 "
                + "FROM " + alias + " WHERE PDEV='" + device + "' AND " + whereClause
                + " ORDER BY PGRP, PDIV, PLNAM, PFNAM";

        System.out.println(sql);
        resultList = iSeries.executeSQLByAliasArray(sql, alias, file);
        for (int i = 0; i < resultList.length; i++) {
            response.setGroup(resultList[0].trim());
            response.setDivision(resultList[1].trim());
            response.setCardNumber(resultList[2].trim());
            response.setLargeFront(resultList[3].trim());
            response.setLargeBack(resultList[4].trim());

            if (i >= 5 && i <= 22) {
                frontLine[i - 5] = resultList[i].trim();
            }
            if (i == 22) {
                response.setFrontLine(frontLine);
            }


            if (i >= 23 && i <= 40) {
                backLine[i - 23] = resultList[i].trim();
            }
            if (i == 40) {
                response.setBackLine(backLine);
            }


            if (i >= 41 && i <= 58) {
                frontModifier[i - 41] = resultList[i].trim();
            }
            if (i == 58) {
                response.setFrontModifier(frontModifier);
            }

            if (i >= 59 && i <= 76) {
                backModifier[i - 59] = resultList[i].trim();
            }
            if (i == 76) {
                response.setBackModifier(backModifier);
            }

            if (i >= 77 && i <= 85) {
                frontLogo[i - 77] = resultList[i].trim();
            }
            if (i == 85) {
                response.setFrontLogo(frontLogo);
            }

            if (i >= 86 && i <= 94) {
                backLogo[i - 86] = resultList[i].trim();
            }
            if (i == 94) {
                response.setBackLogo(backLogo);
            }

        }

        clearIDCARD(grpList, ssnList, device);
        return response;
    }

    private static void clearIDCARD(String[] grpList, String[] ssnList, String device) {
        String alias = "QTEMP.IDCPRV";
        String file = "DFLIB.IDCPRV(BI1)";
        String sql;

        StringBuilder whereClause = new StringBuilder("(");
        whereClause.append("(PGRP='").append(grpList[0]).append("' AND PSSN='").append(ssnList[0]).append("')");
        for (int idx = 1; idx < grpList.length; idx++) {
            whereClause.append(" OR (PGRP='").append(grpList[idx]).append("' AND PSSN='").append(ssnList[idx]).append("')");
        }
        whereClause.append(")");

        sql = "DELETE FROM " + alias + " WHERE PDEV='" + device + "' AND " + whereClause;
        System.out.println("delete:" + sql);
        iSeries.executeSQLByAlias(sql, alias, file);
    }
}
