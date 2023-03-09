package com.login.hth.beans;

import com.login.hth.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoverageImp {

    public ResponseEntity<Object> coverageProfile(String ssn) {
        List<String[]> insure = INSURE.getInsureData(ssn);
        List<String[]> insured = INSURE.getDependentData(ssn);
       // List<String[]> inshst = INSURE.getInshstData(ssn);

        CoverageProfileDTO coverageProfileDTO = new CoverageProfileDTO();
        List<MedicalDTO> medicalDTOList = new ArrayList<>();
        MedicalDTO medicalDTO = new MedicalDTO();
        List<InsuredInformationDTO> insuredInformationDTOList = new ArrayList<>();
        List<DependentInfoDTO> dependentInfoDTOList = new ArrayList<>();
        List<CoverageInfoDTO> coverageInfoDTOList = new ArrayList<>();

        String duplicate = "$150.00";
        String ytDublicateMet = "$100.00";
        String[] effectiveDate = insure.get(0);
        String[] terminationDate = insure.get(0);
        String effectDate = null;
        String terminateDate = null;
        int planIndex = 0;
        for (planIndex = 58; planIndex <=108; planIndex++) {
            if (!effectiveDate[planIndex].equals("0") && terminationDate[planIndex].equals("0")) {
                effectDate = effectiveDate[planIndex];
                terminateDate = terminationDate[planIndex];
                break;
            }
        }
        String plan = insure.get(0)[planIndex];
        System.out.println(coverageProfileDTO);

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2]);
            System.out.println(insureList[i]);

            List<String[]> blackpln = null;
            for (String[] groupDetail : grpmst) {
                InsuredInformationDTO insuredInformationDTO = new InsuredInformationDTO();
                blackpln = INSURE.getBlckplnData(groupDetail[0]);

                insuredInformationDTO.setGroupName(groupDetail[1].trim());
                insuredInformationDTO.setGroupNumber(insureList[2].trim());
                insuredInformationDTO.setDivision(insureList[3].trim());
                insuredInformationDTO.setPrimaryInsureName(insureList[1].trim() + " " + insureList[0].trim());
                insuredInformationDTO.setPrimaryInsureID(ssn);
                insuredInformationDTO.setDateOfBirth(insureList[4].trim());
                insuredInformationDTOList.add(insuredInformationDTO);

            }

            for (String[] dependent : insured) {
                DependentInfoDTO dependentInfoDTO = new DependentInfoDTO();
                dependentInfoDTO.setInsuredName(dependent[1].trim() + " " + dependent[0].trim());
                dependentInfoDTO.setRelationship(dependent[2].trim());
                dependentInfoDTO.setGender(dependent[3].trim());
                dependentInfoDTO.setDDateOfBirth(dependent[4].trim());
                dependentInfoDTO.setDEffectiveDate(dependent[5].trim());
                dependentInfoDTOList.add(dependentInfoDTO);

            }

            for (String[] bPlan : blackpln) {

                CoverageInfoDTO coverageInfoDTO = new CoverageInfoDTO();
                coverageInfoDTO.setPlan(plan);
                coverageInfoDTO.setTypeOfCoverage(bPlan[1]);
                coverageInfoDTO.setEffectiveDate(effectiveDate[planIndex]);
                coverageInfoDTO.setTerminationDate(bPlan[0]);
                coverageInfoDTO.setDeductable(duplicate);
                coverageInfoDTO.setYtDeductableMet(ytDublicateMet);
                coverageInfoDTOList.add(coverageInfoDTO);

            }

        }
        System.out.println("2:"+coverageProfileDTO);

        medicalDTO.setInsuredInformation(insuredInformationDTOList);
        medicalDTO.setDependentInformation(dependentInfoDTOList);
        medicalDTO.setCoverageInformation(coverageInfoDTOList);
        medicalDTOList.add(medicalDTO);
        coverageProfileDTO.setMedical(medicalDTOList);
        coverageProfileDTO.setDental(medicalDTOList);

        System.out.println("@3:"+coverageProfileDTO);

        return new ResponseEntity<>(coverageProfileDTO, HttpStatus.OK);
    }
}