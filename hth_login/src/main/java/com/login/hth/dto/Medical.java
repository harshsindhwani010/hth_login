package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medical {

       public List<InsuredInformationDTO> insuredInformation;
       //public List<CoverageInfoDTO> coverageInformation;
       public List<DependentInfoDTO> dependentInformation;

}
