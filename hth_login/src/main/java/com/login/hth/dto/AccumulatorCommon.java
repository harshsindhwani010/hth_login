package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccumulatorCommon {

    AccumulatorMedicalDTO medical;
    AccumulatorMedicalDTO dental;
    AccumulatorMedicalDTO vision;
    AccumulatorMedicalDTO pharmacy;

}
