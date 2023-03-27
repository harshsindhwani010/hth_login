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
        List<String> document = Arrays.asList("https://blog.photoshelter.com/wp-content/uploads/2016/03/coi-feature.jpg","https://img.yumpu.com/62716007/1/500x640/travel-insurance-certificate.jpg", "https://blog.photoshelter.com/wp-content/uploads/2016/03/coi-feature.jpg", "https://nios.ac.in/media/documents/VocInsServices/m3-f2.pdf");
        jsonObject.put("documents", document);

        return jsonObject;
    }
    public static List<String[]> getDocumentData(String ssn) {
        List<String[]> result = null;
        DocumentImp documentImp = new DocumentImp();
        String[] alias = {"QTEMP.IMAGE"};
        String[] file = {"TESTDATA.IMAGE(TRT)"};
        String sql = "SELECT IIMG, IGRPID, IBLKID,IBLKPL FROM QTEMP.IMAGE WHERE ISSN ='" + ssn +"'";
        result = iSeries.executeSQLByAlias(sql, alias, file);
        return result;

}
}
