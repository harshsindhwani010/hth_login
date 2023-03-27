package com.login.hth.beans;

import com.login.hth.dto.InsuredInformationDTO;
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

        List<String[]> blackpln = null;
        for (String[] groupDetail : grpmst) {
            InsuredInformationDTO insuredInformationDTO = new InsuredInformationDTO();
            blackpln = INSURE.getBlckplnData(groupDetail[0], groupDetail[p], plan);
            if (!blackpln.get(0).equals("")){
                INSURE.getPlanDes(plan);
        }

        List<String[]> data = DocumentsData.getDocumentData(ssn);
        String[] images = new String[data.size()];
        for (int a = 0; a < data.size(); a++) {
            images[a]=imageUrl + data.get(a)[0].trim();
        }
        return images;
    }
    }


        return new String[0];
    }
}

