package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageInfoDTO {

    //Coverage Information

    private String plan;
    private String typeOfCoverage;
    private String effectiveDate;
    private String terminationDate;
    private String deductable;
    private String ytDeductableMet;
}
