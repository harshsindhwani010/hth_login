package com.login.hth.beans;

import com.login.hth.security.iSeries;

import java.util.List;

public class IDWORK {
	private static String[] grpList = null;
	
	public static String[] getInQueueID(String usr, String ssn) {
		List<String[]> resultList;
		String alias = "QTEMP.IDWORK";
		String file = "HTHDATV1.IDWORK(ART)";

		String sql = "SELECT DISTINCT IEMPID, IGRP FROM QTEMP.IDWORK WHERE IUSER = '" + usr + "' OR IEMPID= '"+ssn+"'";
		resultList = iSeries.executeSQLByAlias(sql, alias, file);

		String[] idList = new String[resultList.size()];
		grpList = new String[resultList.size()];
		for (int idx = 0; idx < idList.length; idx++) {
			idList[idx] = resultList.get(idx)[0].trim();
			grpList[idx] = resultList.get(idx)[1].trim();
		}
		
		cleanInQueueID(usr);
		
		return idList;
	}

    public static String[] getGrpList() {
        return grpList;
    }
    
	private static void cleanInQueueID(String usr) {
		String alias = "QTEMP.IDWORK";
		String file = "HTHDATV1.IDWORK(ART)";

		String sql = "DELETE FROM QTEMP.IDWORK WHERE IUSER='" + usr + "'";
		iSeries.executeSQLByAlias(sql, alias, file);
	}
}
