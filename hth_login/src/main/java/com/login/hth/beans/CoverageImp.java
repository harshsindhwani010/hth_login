package com.login.hth.beans;

import com.login.hth.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoverageImp {

    public ResponseEntity<CoverageProfileDTO> coverageProfile(String ssn) {
        List<String[]> insure = INSURE.getInsureData(ssn);
        List<String[]> insured = INSURE.getDependentData(ssn);
        List<String[]> inshst = INSURE.getInshstData(ssn);

        CoverageProfileDTO coverageProfileDTO = new CoverageProfileDTO();
        List<MedicalDTO> medicalDTO = new ArrayList<>();
        MedicalDTO medical1 = new MedicalDTO();
        List<InsuredInformationDTO> wholeDTOList = new ArrayList<InsuredInformationDTO>();
        List<InsuredInformationDTO> informationDTO = new ArrayList<>();
        List<DependentInfoDTO> dependentDTO = new ArrayList<>();
        List<CoverageInfoDTO> coverageDTO = new ArrayList<>();

        String duplicate = "$150.00";
        String ytDublicateMet = "$100.00";
        String[] effectiveDate = insure.get(0);
        String[] terminationDate = insure.get(0);
        String effectDate = null;
        String terminateDate = null;
        int planIndex = 0;
        for (planIndex = 7; planIndex <= 57; planIndex++) {
            if (!effectiveDate[planIndex].equals("0") && terminationDate[planIndex].equals("0")) {
                effectDate = effectiveDate[planIndex];
                terminateDate = terminationDate[planIndex];

                break;
            }
        }
        String plan = insure.get(0)[planIndex];

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2]);
            System.out.println(insureList[i]);

            CoverageInfoDTO coverageInfoDTO = new CoverageInfoDTO();

            List<String[]> blackpln = null;
            for (String[] groupDetail : grpmst) {
                InsuredInformationDTO insuredInformationDTO = new InsuredInformationDTO();
                // blackpln = INSURE.getBlckplnData(groupDetail[0]);

                insuredInformationDTO.setGroupName(groupDetail[1].trim());
                insuredInformationDTO.setGroupNumber(insureList[2].trim());
                insuredInformationDTO.setDivision(insureList[3].trim());
                insuredInformationDTO.setPrimaryInsureName(insureList[1].trim() + " " + insureList[0].trim());
                insuredInformationDTO.setPrimaryInsureID(ssn);
                insuredInformationDTO.setDateOfBirth(insureList[4].trim());
                informationDTO.add(insuredInformationDTO);

            }

            for (String[] dependent : insured) {
                DependentInfoDTO dependentInfoDTO = new DependentInfoDTO();
                dependentInfoDTO.setInsuredName(dependent[1].trim() + " " + dependent[0].trim());
                dependentInfoDTO.setRelationship(dependent[2].trim());
                dependentInfoDTO.setGender(dependent[3].trim());
                dependentInfoDTO.setDDateOfBirth(dependent[4].trim());
                dependentInfoDTO.setDEffectiveDate(dependent[5].trim());
                dependentDTO.add(dependentInfoDTO);

            }
            medical1.setInsuredInformation(informationDTO);
            medical1.setDependentInformation(dependentDTO);
            medicalDTO.add(medical1);
            coverageProfileDTO.medical = medicalDTO;
            coverageProfileDTO.dental = medicalDTO;


//            for (String[] bPlan : blackpln) {
//                coverageInfoDTO.setPlan(plan);
//                coverageInfoDTO.setTypeOfCoverage(bPlan[1]);
//                coverageInfoDTO.setEffectiveDate(effectiveDate[planIndex]);
//                coverageInfoDTO.setTerminationDate(bPlan[0]);
//                coverageInfoDTO.setDeductable(duplicate);
//                coverageInfoDTO.setYtDeductableMet(ytDublicateMet);
//                coverageDTO.setCoverageInfoDTO(coverageInfoDTO);
//                coverageInfoDTO.setTypeOfCoverage(coverageInfoDTO.getTypeOfCoverage());

//                wholeDTOList.add((InsuredInformationDTO) coverageDTO);
//                wholeDTOList.add((InsuredInformationDTO) dependentDTO);
//                MedicalDTO medical1 = new MedicalDTO();
//                medical1.setInsuredInformation(wholeDTOList);
//                 dental = new Dental();
//                dental.setInsuredInformationDTO(wholeDTOList);
//                VisionDTO vision = new VisionDTO();
//                vision.getInsuredInfo(wholeDTOList);
//                System.out.println(grpmst.get(i));
        }

        return ResponseEntity.ok().body(coverageProfileDTO);
    }

}




