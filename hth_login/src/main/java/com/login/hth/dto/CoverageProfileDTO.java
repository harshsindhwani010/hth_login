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
    @JsonProperty("medical")
    private List<CoverageMedicalDTO> medical;
    @JsonProperty("dental")
    private List<CoverageDentalDTO> dental;
    @JsonProperty("vision")
    private List<CoverageVisionDTO> vision;
}
