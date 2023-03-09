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
    private List<MedicalDTO> Medical;
    @JsonProperty("Dental")
    private List<MedicalDTO> Dental;
    @JsonProperty("Vision")
    private String Vision;
}
