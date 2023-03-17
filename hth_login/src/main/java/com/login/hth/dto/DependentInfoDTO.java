package com.login.hth.dto;

import com.login.hth.utils.GenderType;
import com.login.hth.utils.RelationType;
//import com.login.hth.utils.RelationType;
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

    public String getGender() {
        return gender.label;
    }
    public String getRelationShip() {
        return relationship.label;
    }

}




