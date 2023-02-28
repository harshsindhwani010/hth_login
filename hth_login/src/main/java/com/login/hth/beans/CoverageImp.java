package com.login.hth.beans;

import com.login.hth.dto.CoverageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CoverageImp {

    public ResponseEntity<Object> coverageProfile(String ssn) {
        List<String[]> insure = INSURE.getInsureData(ssn);
        List<String[]> insured = INSURE.getDependentData(ssn);
        List<String[]> inshst = INSURE.getInshstData(ssn);
        List<CoverageDTO> wholeDTOList = new ArrayList<CoverageDTO>();

        String[] name = Arrays.stream(insure.get(0)).map(String::trim).toArray(String[]::new);
        String fullName = String.join(" ", name);

        String[] insured_name = Arrays.stream(insured.get(0)).map(String::trim).toArray(String[]::new);
        String insured_fullName = String.join(" ", insured_name);

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);
            CoverageDTO coverageDTO = new CoverageDTO();

            List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2].trim());
            // String[] planList = new String[resultList.size()];

            for (String[] detail : grpmst) {
                System.out.println(detail[i]);
                List<String[]> blckpln = INSURE.getBlckplnData(detail[0].trim());
                for (String[] blckplnDetail : blckpln) {

                    coverageDTO.setGroupName(detail[1].trim());
                    coverageDTO.setGroupNumber(insureList[2].trim());
                    coverageDTO.setDivision(insureList[3].trim());
                    coverageDTO.setPrimaryInsureName(fullName);
                    coverageDTO.setPrimaryInsureID(ssn);
                    // coverageDTO.setPlan(grpmst);
                    coverageDTO.setTypeOfCoverage(blckplnDetail[5]);
                    for (String[] detailed : inshst) {
                        if (insureList[6].equals(null)) {
                            if (!detailed[8].equals(null)) {
                                coverageDTO.setTerminationDate(detailed[6].trim());
                            }
                        } else {
                            coverageDTO.setTerminationDate("Terminated");
                        }
                    }
                }
                    for (String[] insuredDetail : insured) {
                        coverageDTO.setInsuredName(insured_fullName);
                        coverageDTO.setRelationship(insuredDetail[2].trim());
                        coverageDTO.setGender(insuredDetail[3].trim());
                        coverageDTO.setDateOfBirth(insuredDetail[4].trim());
                        coverageDTO.setEffectiveDate(insuredDetail[5].trim());
                        wholeDTOList.add(coverageDTO);
                    }
            }
        }
        return ResponseEntity.accepted().body(coverageProfile(ssn));
    }
}
