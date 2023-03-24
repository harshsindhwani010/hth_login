package com.login.hth.beans;


import com.login.hth.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccumulatorImp {
    public AccumulatorCommon accumulatorProfile(String ssn) {
        List<String[]> commonResult = Accumulator.accumulatorData(ssn);
        return setData(commonResult.get(0)[4].trim(), commonResult);
    }

    private AccumulatorCommon setData(String type, List<String[]> commonResult){
        AccumulatorCommon accumulatorCommon = new AccumulatorCommon();
        if(type.equals("M")){
            accumulatorCommon.setMedical(aCommon(commonResult));
        }
        if(type.equals("V")){
            accumulatorCommon.setVision(aCommon(commonResult));
        }
        if(type.equals("D")){
            accumulatorCommon.setDental(aCommon(commonResult));
        }
        if(type.equals("RX")){
            accumulatorCommon.setPharmacy(aCommon(commonResult));
        }
        return accumulatorCommon;
    }

    private List<AccumulatorMedicalDTO> aCommon(List<String[]> commonResult){
        AccumulatorMedicalDTO accumulatorMedicalDTO = new AccumulatorMedicalDTO();
        accumulatorMedicalDTO.setTotal(commonResult.get(0)[1].trim());
        accumulatorMedicalDTO.setMet(commonResult.get(0)[2].trim());
        String value = (commonResult.get(0)[1].trim());
        if(commonResult.get(0)[2].trim().equals("")) {
            value = String.valueOf(0);
        }
        accumulatorMedicalDTO.setRemaining(String.valueOf(Double.parseDouble(commonResult.get(0)[1].trim())-Double.parseDouble(value)));
        List<AccumulatorMedicalDTO> accumulatorMedicalDTOS = new ArrayList<>();
        accumulatorMedicalDTOS.add(accumulatorMedicalDTO);
        return accumulatorMedicalDTOS;
    }

}


