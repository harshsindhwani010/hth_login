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

       public List<InsuredInformationDTO> insuredInformation;
       public List<CoverageInfoDTO> coverageInformation;
       public List<DependentInfoDTO> dependentInformation;

}
