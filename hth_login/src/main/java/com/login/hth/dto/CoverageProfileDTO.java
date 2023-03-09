package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageProfileDTO {

    public List<MedicalDTO> medical;
    public List<MedicalDTO> dental;
    public String vision;
}
