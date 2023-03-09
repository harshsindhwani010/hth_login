package com.login.hth.dto;

import com.login.hth.utils.CoverageType;
import com.login.hth.utils.RelationType;
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
