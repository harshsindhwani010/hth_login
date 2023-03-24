package com.login.hth.beans;


import com.login.hth.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccumulatorImp {
    public AccumulatorCommon accumulatorProfile(String ssn) {
        AccumulatorCommon accumulatorCommon = new AccumulatorCommon();
        List<String[]> commonResult = Accumulator.accumulatorData(ssn);
        return setData(commonResult.get(0)[4].trim(), commonResult);
    }

    private AccumulatorCommon setData(String type, List<String[]> commonResult){
        AccumulatorMedicalDTO accumulatorMedicalDTO = new AccumulatorMedicalDTO();
        AccumulatorCommon accumulatorCommon = new AccumulatorCommon();
        if(type.equals("M")){
            accumulatorMedicalDTO.setTotal(commonResult.get(0)[1].trim());
            accumulatorMedicalDTO.setMet(commonResult.get(0)[2].trim());
            String value = (commonResult.get(0)[1].trim());
            if(commonResult.get(0)[2].trim().equals("")) {
                value = String.valueOf(0);
            }
            accumulatorMedicalDTO.setRemaining(String.valueOf(Double.parseDouble(commonResult.get(0)[1].trim())-Double.parseDouble(value)));
        }
        accumulatorCommon.setMedical(accumulatorMedicalDTO);
        accumulatorCommon.setVision(accumulatorMedicalDTO);
        accumulatorCommon.setDental(accumulatorMedicalDTO);
        accumulatorCommon.setPharmacy(accumulatorMedicalDTO);
        return accumulatorCommon;
    }

}


