package com.login.hth.beans;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DocumentImp {
    private final String imageUrl = "https://services.hi-techhealth.com";
    public String[] getDocument(String ssn) {
        String groupId,blockid,planId;
        List<String[]> insure = INSURE.getInsureData(ssn);
        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2]);
            String[] plans = grpmst.get(0);
            String plan = null;
            int p = 0;
            for (p = 2; p <= 51; p++) {
                if (!plans[p].equals("0")) {
                    plan = plans[p];
                    System.out.println(plans);
                    break;
                }
            }
        }

            List<String[]> data = DocumentsData.getDocumentData(ssn);
        String[] images = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            images[i]=imageUrl + data.get(i)[0].trim();
        }
        return images;
    }


}


