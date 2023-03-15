package com.login.hth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DentalDTO {

    private List<InsuredInformationDTO> insuredInformation;
    private List<CoverageInfoDTO> coverageInformation;
    private List<DependentInfoDTO> dependentInformation;

}
