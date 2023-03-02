package com.login.hth.beans;

import com.login.hth.dto.CoverageInfoDTO;
import com.login.hth.dto.DependentInfoDTO;
import com.login.hth.dto.InsuredInformationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoverageImp {

    public ResponseEntity<Object> coverageProfile(String ssn) {

        List<String[]> insure = INSURE.getInsureData(ssn);
        List<String[]> insured = INSURE.getDependentData(ssn);
        List<String[]> inshst = INSURE.getInshstData(ssn);
        List<InsuredInformationDTO> wholeDTOList = new ArrayList<InsuredInformationDTO>();

        String duplicate = "$150.00";
        String ytDublicateMet = "$100.00";
        String[] effectiveDate = insure.get(0);
        String[] terminationDate = insure.get(0);
        String effectDate = null;
        int planIndex=0;
        for(planIndex=7;planIndex<=57;planIndex++){
            if(!effectiveDate[planIndex].equals("0") && terminationDate[planIndex].equals("0")){
                effectDate = effectiveDate[planIndex];
                effectDate = terminationDate[planIndex];

                break;
            }
        }
        String plan =insure.get(0)[planIndex];

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2]);
            System.out.println(insureList[i]);

            for (String[] groupDetail : grpmst) {
               List <String[]> blackpln = INSURE.getBlckplnData(groupDetail[0]);

                InsuredInformationDTO coverageDTO = new InsuredInformationDTO();
                CoverageInfoDTO coverageInfoDTO = new CoverageInfoDTO();
                coverageDTO.setGroupName(groupDetail[1].trim());
                coverageDTO.setGroupNumber(insureList[2].trim());
                coverageDTO.setDivision(insureList[3].trim());
                coverageDTO.setPrimaryInsureName(insureList[1].trim()+" "+insureList[0].trim());
                coverageDTO.setPrimaryInsureID(ssn);
                coverageDTO.setDateOfBirth(insureList[4].trim());

                for (String[] dependent : insured) {
                    DependentInfoDTO dependentInfoDTO = new DependentInfoDTO();
                    dependentInfoDTO.setInsuredName(dependent[1].trim()+" "+dependent[0].trim());
                    dependentInfoDTO.setRelationship(dependent[2].trim());
                    dependentInfoDTO.setGender(dependent[3].trim());
                    dependentInfoDTO.setDDateOfBirth(dependent[4].trim());
                    dependentInfoDTO.setDEffectiveDate(dependent[5].trim());

                    coverageDTO.setDependentInfoDTO(dependentInfoDTO);

                    for (String[] bPlan : blackpln) {
                        coverageInfoDTO.setPlan(plan);
                        coverageInfoDTO.setTypeOfCoverage(bPlan[1]);
                        coverageInfoDTO.setEffectiveDate(effectDate);
                        coverageInfoDTO.setTerminationDate(bPlan[0]);
                        coverageInfoDTO.setDeductable(duplicate);
                        coverageInfoDTO.setYtDeductableMet(ytDublicateMet);
                        coverageDTO.setCoverageInfoDTO(coverageInfoDTO);

                        wholeDTOList.add(coverageDTO);
                        System.out.println(groupDetail[i]);
                    }
                }
            }
        }
        return ResponseEntity.ok().body(wholeDTOList);
    }

}
