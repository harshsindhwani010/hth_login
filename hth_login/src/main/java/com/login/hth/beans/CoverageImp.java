package com.login.hth.beans;

import com.login.hth.dto.CoverageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CoverageImp {

    public ResponseEntity<Object> coverageProfile(String ssn) {
        List<String[]> insure = INSURE.getInsureData(ssn);
        String[] name = Arrays.stream(insure.get(0)).map(String::trim).toArray(String[]::new);
        String fullName = String.join(" ", name);

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getgrpmstData(insureList[2]);
            //List<String[]> blckpln = INSURE.getblckplnData(grpmst[0]);
            // String[] planList = new String[resultList.size()];

            for (String[] detail : grpmst) {
                CoverageDTO coverageDTO = new CoverageDTO();
                coverageDTO.setGroupName(detail[1]);
                coverageDTO.setGroupNumber(insureList[2]);
                coverageDTO.setDivision(insureList[3]);
                coverageDTO.setPrimaryInsureName(fullName);
                coverageDTO.setPrimaryInsureID(ssn);
                // coverageDTO.setPlan(grpmst);
                //coverageDTO.setTypeOfCoverage();
            }
        }
        return ResponseEntity.accepted().body(coverageProfile(ssn));
    }
}
