package com.login.hth.dto;

import com.login.hth.utils.GenderType;
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
    private GenderType gender;
    private String dDateOfBirth;
    private String dEffectiveDate;

    public GenderType getGender() {
        return gender;
    }

}




