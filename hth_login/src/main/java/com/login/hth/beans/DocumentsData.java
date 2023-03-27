package com.login.hth.beans;

import com.login.hth.security.iSeries;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DocumentsData {



    public JSONObject document() {
        JSONObject jsonObject = new JSONObject();
        List<String> document = Arrays.asList("https://blog.photoshelter.com/wp-content/uploads/2016/03/coi-feature.jpg","https://img.yumpu.com/62716007/1/500x640/travel-insurance-certificate.jpg", "https://blog.photoshelter.com/wp-content/uploads/2016/03/coi-feature.jpg", "https://services.hi-techhealth.com/TR1/upload/regular/MGA11401_A67574286500520210525164007.pdf");
        jsonObject.put("documents", document);

        return jsonObject;
    }

    public static List<String[]> getDocumentDataWithGroupID(String groupId) {
        List<String[]> result = null;
        DocumentImp documentImp = new DocumentImp();
        String[] alias = {"QTEMP.IMAGE"};
        String[] file = {"TESTDATA.IMAGE(TRT)"};
        String sql = "SELECT IIMG FROM QTEMP.IMAGE WHERE IGRPNO = '"+groupId+"'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;
    }
    public static List<String[]> getDocumentData(String blockId, String planId) {
        List<String[]> result = null;
        DocumentImp documentImp = new DocumentImp();
        String[] alias = {"QTEMP.IMAGE" };
        String[] file = {"TESTDATA.IMAGE(TRT)"};
        String sql = "SELECT IIMG FROM QTEMP.IMAGE JOIN QTEMP.INSURE WHERE IBLKID='"+ blockId+"' AND IBLKPL ='"+planId+"'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;
}
}
