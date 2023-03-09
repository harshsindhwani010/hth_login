package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDTO {

       private List<InsuredInformationDTO> insuredInformation;
       private List<CoverageInfoDTO> coverageInformation;
       private List<DependentInfoDTO> dependentInformation;

}
