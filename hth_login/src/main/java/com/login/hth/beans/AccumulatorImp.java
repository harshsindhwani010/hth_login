package com.login.hth.beans;


import com.login.hth.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccumulatorImp {
    public AccumulatorCommon accumulatorProfile(String ssn) {
        List<String[]> commonResult = Accumulator.accumulatorData(ssn);
        if (commonResult.size() > 0) {
        return setData(commonResult.get(0)[4].trim(), commonResult);
    }else {
       return new AccumulatorCommon(commonResult);
        }
    }

    private AccumulatorCommon setData(String type, List<String[]> commonResult) {
        AccumulatorCommon accumulatorCommon = new AccumulatorCommon(commonResult);
        for (int i = 0; i < commonResult.size(); i++) {
            if (commonResult.get(i)[4].trim().equals("M")) {
                accumulatorCommon.setMedical(aCommon(commonResult.get(i)));
            }
            if (commonResult.get(i)[4].trim().equals("V")) {
                accumulatorCommon.setVision(aCommon(commonResult.get(i)));
            }
            if (commonResult.get(i)[4].trim().equals("D")) {
                accumulatorCommon.setDental(aCommon(commonResult.get(i)));
            }
            if (commonResult.get(i)[4].trim().equals("R")) {
                accumulatorCommon.setPharmacy(aCommon(commonResult.get(i)));
            }

            }
        return accumulatorCommon;
    }


    private List<AccumulatorMedicalDTO> aCommon(String[] commonResult){
        AccumulatorMedicalDTO accumulatorMedicalDTO = new AccumulatorMedicalDTO();

            accumulatorMedicalDTO.setTotal(commonResult[1].trim());
            if (commonResult[2].trim().equals("Y")) {
                accumulatorMedicalDTO.setMet(commonResult[1]);
            } else{
                accumulatorMedicalDTO.setMet("0");
            }


            String deductible = "4.1";
            String coinsurance = "7.1";
            String value = (commonResult[1].trim() );
            if (commonResult[2].trim().equals("") ||commonResult[3].trim().equals("") ) {
                value = String.valueOf(0 + "0");
            }
            accumulatorMedicalDTO.setDeductible(deductible);
            accumulatorMedicalDTO.setCoinsurance(coinsurance);
            accumulatorMedicalDTO.setRemaining(String.valueOf(Double.parseDouble(commonResult[1].trim()) - Double.parseDouble(value)));
            List<AccumulatorMedicalDTO> accumulatorMedicalDTOS = new ArrayList<>();
            accumulatorMedicalDTOS.add(accumulatorMedicalDTO);
         return accumulatorMedicalDTOS;
    }

    }


