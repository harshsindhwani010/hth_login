package com.login.hth.beans;

import com.login.hth.dto.*;
import com.login.hth.utils.CoverageType;
import com.login.hth.utils.Relation2;
import com.login.hth.utils.GenderType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.login.hth.beans.ClaimsData.formatDates;

@Component
public class CoverageImp {

    public ResponseEntity<Object> coverageProfile(String ssn) {
        List<String[]> insure = INSURE.getInsureData(ssn);
        List<String[]> insured = INSURE.getDependentData(ssn);
        List<String[]> inshst = INSURE.getInshstData(ssn);


        CoverageProfileDTO coverageProfileDTO = new CoverageProfileDTO();
        List<CoverageMedicalDTO> coverageMedicalDTOList = new ArrayList<>();
        CoverageMedicalDTO coverageMedicalDTO = new CoverageMedicalDTO();
        List<CoverageDentalDTO> coverageDentalDTOList = new ArrayList<>();
        CoverageDentalDTO coverageDentalDTO = new CoverageDentalDTO();
        List<CoverageVisionDTO> coverageVisionDTOList = new ArrayList<>();
        CoverageVisionDTO coverageVisionDTO = new CoverageVisionDTO();

        List<InsuredInformationDTO> insuredInformationDTOList = new ArrayList<>();
        List<DependentInfoDTO> dependentInfoDTOList = new ArrayList<>();
        List<CoverageInfoDTO> coverageInfoDTOList = new ArrayList<>();

//        String duplicate = "$150.00";
        String ytDublicateMet = "$100.00";
        String[] effectiveDate = insure.get(0);
        String[] terminationDate = insure.get(0);
        String effectDate = null;
        String terminateDate = null;
        int planIndex = 0;
        for (planIndex = 58; planIndex <= 108; planIndex++) {
            if (effectiveDate[planIndex].equals("0") && terminationDate[planIndex].equals("0")) {
                effectDate = effectiveDate[planIndex];
                terminateDate = terminationDate[planIndex];
                break;
            }
        }
        //String plan = insure.get(0)[planIndex];
        System.out.println(coverageProfileDTO);
        String[] ePlans = insure.get(0);
        String[] futuretermination = inshst.get(0);
        int tDate = 0;
        int enrolledPlans = 0;
        String enrollPlan = null;
        for (enrolledPlans = 7; enrolledPlans <= 57; enrolledPlans++) {
            enrollPlan = ePlans[enrolledPlans];

            for (tDate = 0; tDate <= 5; tDate++) {
                if (!ePlans[enrolledPlans].equals(" ")) {

        for (int i = 0; i < insure.size(); i++) {
            String[] insureList = insure.get(i);

            List<String[]> grpmst = INSURE.getGroupMasterData(insureList[2]);

            String[] plans = grpmst.get(0);
            String plan = null;
            int p = 0;
            for (p = 2; p <= 51; p++) {
                if (!plans[p].equals("0")) {
                    plan = plans[p];
                    break;
                }
            }

            System.out.println(insureList[i]);

            List<String[]> blackpln = null;
            for (String[] groupDetail : grpmst) {
                InsuredInformationDTO insuredInformationDTO = new InsuredInformationDTO();
                //check blank
                blackpln = INSURE.getBlckplnData(groupDetail[0], groupDetail[p], plan);
                if (!blackpln.get(0).equals("")){
                    INSURE.getPlanDes(plan);
                }

                insuredInformationDTO.setGroupName(groupDetail[1].trim());
                insuredInformationDTO.setGroupNumber(insureList[2].trim());
                insuredInformationDTO.setDivision(insureList[3].trim());
                insuredInformationDTO.setPrimaryInsureName(insureList[1].trim() + " " + insureList[0].trim());
                insuredInformationDTO.setPrimaryInsureID(ssn);
                insuredInformationDTO.setDateOfBirth(formatDates(insureList[4].trim()));
                insuredInformationDTOList.add(insuredInformationDTO);

            }

            for (String[] dependent : insured) {
                DependentInfoDTO dependentInfoDTO = new DependentInfoDTO();
                dependentInfoDTO.setInsuredName(dependent[1].trim() + " " + dependent[0].trim());
                dependentInfoDTO.setRelationship(Relation2.mapper.get(dependent[2].trim()));
                dependentInfoDTO.setGender(GenderType.valueOf(dependent[3].trim()));
                if (isStringInt(dependent[2].trim())){
                    dependentInfoDTO.setRelationship(dependent[2].trim() + "" + Relation2.mapper.get("num"));
                }else {
                    dependentInfoDTO.setRelationship(Relation2.mapper.get(dependent[2].trim()));
                }
                dependentInfoDTO.setGender(GenderType.valueOf(dependent[3].trim()));
                dependentInfoDTO.setDDateOfBirth(formatDates(dependent[4].trim()));
                dependentInfoDTO.setDEffectiveDate(formatDates(dependent[5].trim()));
                dependentInfoDTOList.add(dependentInfoDTO);

            }

            for (String[] bPlan : blackpln) {
                CoverageInfoDTO coverageInfoDTO = new CoverageInfoDTO();
                coverageInfoDTO.setPlan(INSURE.getPlanDes(plan).get(0)[0].trim());

                coverageInfoDTO.setTypeOfCoverage(CoverageType.mapper.get(bPlan[0]));
                coverageInfoDTO.setEffectiveDate(effectDate);
                coverageInfoDTO.setTerminationDate(terminateDate);
                coverageInfoDTO.setDeductable(INSURE.getClmdet(INSURE.getClmHdr(ssn).get(0)[0].trim()).get(0)[18].trim());
                coverageInfoDTO.setYtDeductableMet(ytDublicateMet);
                coverageInfoDTOList.add(coverageInfoDTO);

            }

            System.out.println("2:" + coverageProfileDTO);
            if (coverageInfoDTOList.get(0).getTypeOfCoverage().equalsIgnoreCase("Medical")) {
                coverageMedicalDTO.setInsuredInformation(insuredInformationDTOList);
                coverageMedicalDTO.setDependentInformation(dependentInfoDTOList);
                coverageMedicalDTO.setCoverageInformation(coverageInfoDTOList);
                coverageMedicalDTOList.add(coverageMedicalDTO);
                coverageProfileDTO.setMedical(coverageMedicalDTOList);
            } else if (coverageInfoDTOList.get(0).getTypeOfCoverage().equalsIgnoreCase("Dental")) {
                coverageDentalDTO.setInsuredInformation(insuredInformationDTOList);
                coverageDentalDTO.setDependentInformation(dependentInfoDTOList);
                coverageDentalDTO.setCoverageInformation(coverageInfoDTOList);
                coverageDentalDTOList.add(coverageDentalDTO);
                coverageProfileDTO.setDental(coverageDentalDTOList);
            } else {
                coverageVisionDTO.setInsuredInformation(insuredInformationDTOList);
                coverageVisionDTO.setDependentInformation(dependentInfoDTOList);
                coverageVisionDTO.setCoverageInformation(coverageInfoDTOList);
                coverageVisionDTOList.add(coverageVisionDTO);
                coverageProfileDTO.setVision(coverageVisionDTOList);
            }

            System.out.println("@3:" + coverageProfileDTO);

                }
                }else if (!futuretermination.equals(" ")) {
                    return new ResponseEntity<>(coverageProfileDTO, HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>("User have no plans", HttpStatus.BAD_REQUEST);
                }
            }
        }

        return new ResponseEntity<>(coverageProfileDTO, HttpStatus.OK);
        }
//    }

    public boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}