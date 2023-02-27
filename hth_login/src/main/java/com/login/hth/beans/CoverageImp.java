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
        List<String[]> insured = INSURE.getDependentData(ssn);
        String[] name = Arrays.stream(insure.get(0)).map(String::trim).toArray(String[]::new);
        String fullName = String.join(" ", name);

        String[] insured_name= Arrays.stream(insured.get(0)).map(String::trim).toArray(String[]::new);
        String insured_fullName = String.join(" ", insured_name);

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getgrpmstData(insureList[2]);
            List<String[]> inshst = INSURE.getInshstData(ssn);
//            List<String[]> blckpln = INSURE.getblckplnData(coverageProfile([5]));
//             String[] planList = new String[resultList.size()];

            for (String[] detail : grpmst) {
                for (String[] insuredDetail : insured) {

                    CoverageDTO coverageDTO = new CoverageDTO();
                    coverageDTO.setGroupName(detail[1]);
                    coverageDTO.setGroupNumber(insureList[2]);
                    coverageDTO.setDivision(insureList[3]);
                    coverageDTO.setPrimaryInsureName(fullName);
                    coverageDTO.setPrimaryInsureID(ssn);
                    // coverageDTO.setPlan(grpmst);
//                    coverageDTO.setTypeOfCoverage([6]);
                    for (String[] detailed : inshst) {
                        if (insureList[6].equals(null)) {

                            if (!detailed[8].equals(null)) {
                                coverageDTO.setTerminationDate(detailed[6]);
                            }
                        } else {
                            coverageDTO.setTerminationDate("Terminated");
                        }
                    }
                    coverageDTO.setInsuredName(insured_fullName);
                    coverageDTO.setRelationship(insuredDetail[2]);
                    coverageDTO.setGender(insuredDetail[3]);
                    coverageDTO.setDateOfBirth(insuredDetail[4]);
                    coverageDTO.setEffectiveDate(insuredDetail[5]);

                }
            }
        }
        return ResponseEntity.accepted().body(coverageProfile(ssn));
    }
}
