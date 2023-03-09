package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageProfileDTO {

    public MedicalDTO Medical;
    public MedicalDTO Dental;
    public String Vision;
}
