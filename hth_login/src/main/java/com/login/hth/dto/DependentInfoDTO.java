package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependentInfoDTO {

    //Dependent Information

    private String insuredName;
    private String relationship;
    private String gender;
    private String dDateOfBirth;
    private String dEffectiveDate;

}
