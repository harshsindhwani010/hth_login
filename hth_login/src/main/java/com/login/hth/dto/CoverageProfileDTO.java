package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageProfileDTO {

    public List<MedicalDTO> Medical;
    public List<MedicalDTO> Dental;
    public String Vision;
}
