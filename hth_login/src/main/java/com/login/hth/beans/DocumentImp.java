package com.login.hth.beans;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DocumentImp {
    private final String imageUrl = "https://services.hi-techhealth.com";

    public String[] getDocument(String ssn) {
        List<String[]> insure = INSURE.getInsureData(ssn);
        String groupId= insure.get(0)[2].trim();
        List<String[]> documentData;
        documentData = DocumentsData.getDocumentDataWithGroupID(groupId);
        if(documentData.size()>0){

        }else{

            for (int i = 0; i < insure.size(); i++) {
                String[] insureList = insure.get(i);

                List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2]);
                String[] plans = grpmst.get(0);
                String plan = null;
                String blockId = null;
                int p = 0;
                for (p = 2; p <= 51; p++) {
                    if (!plans[p].equals("0")) {
                        plan = plans[p].trim();
                        blockId =plans[0].trim();
                        break;
                    }
                }

                documentData = DocumentsData.getDocumentData(blockId,plan);

        }
            }
        String[] images = new String[documentData.size()];
        for (int a = 0; a < documentData.size(); a++) {
            images[a] = imageUrl + documentData.get(a)[0].trim();
        }
        return images;
    }
    }



