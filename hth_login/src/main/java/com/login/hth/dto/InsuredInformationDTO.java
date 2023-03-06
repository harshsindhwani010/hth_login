package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuredInformationDTO {

    //Insured Information

    private String groupName;
    private String groupNumber;
    private String division;
    private String primaryInsureName;
    private String primaryInsureID;
    private String dateOfBirth;
//    private CoverageInfoDTO CoverageInformation;
//    private DependentInfoDTO DependentInformation;




}
