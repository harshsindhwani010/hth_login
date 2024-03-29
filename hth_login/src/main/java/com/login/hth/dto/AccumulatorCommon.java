package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccumulatorCommon {

    List<AccumulatorMedicalDTO> medical;
    List<AccumulatorMedicalDTO> dental;
    List<AccumulatorMedicalDTO> vision;
    List<AccumulatorMedicalDTO> pharmacy;


    public AccumulatorCommon(List<String[]> commonResult) {
    }
}
