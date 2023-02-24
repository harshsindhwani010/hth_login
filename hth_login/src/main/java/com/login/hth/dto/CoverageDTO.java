package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageDTO {

    //Insured Information

    private String groupName;
    private String groupNumber;
    private String division;
    private String primaryInsureName;
    private String primaryInsureID;
    private String dateOfBirth;

    //Coverage Information

    private String plan;
    private String typeOfCoverage;
    private String effectiveDate;
    private String terminationDate;
    private String deductable;
    private String ytDeductableMet;

    //Dependent Information

    private String insuredName;
    private String relationship;
    private String gender;
    private String dDateOfBirth;
    private String dEffectiveDate;


}
