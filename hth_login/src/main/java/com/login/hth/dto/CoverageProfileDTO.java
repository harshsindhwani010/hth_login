package com.login.hth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageProfileDTO {
    @JsonProperty("Medical")
    private List<CoverageMedicalDTO> Medical;
    @JsonProperty("Dental")
    private List<CoverageDentalDTO> Dental;
    @JsonProperty("Vision")
    private List<CoverageVisionDTO> Vision;
}
