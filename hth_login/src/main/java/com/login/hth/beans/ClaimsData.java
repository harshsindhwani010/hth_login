package com.login.hth.beans;

import com.login.hth.dto.ClaimResponseDTO;
import com.login.hth.dto.PaymentDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component

public class ClaimsData {
    public ResponseEntity<Object> checkClaim(String ssn){
        List<String[]> headerList = CLMDET.getHeaderData(ssn);
        List<ClaimResponseDTO> wholeDTOList = new ArrayList<ClaimResponseDTO>();
        List<String[]> insureList = CLMDET.getInsureData(ssn);
        String[] name = Arrays.stream(insureList.get(0)).map(String::trim).toArray(String[]::new);
        String fullName = String.join(" ", name);
        for(int i=0;i<headerList.size();i++){
            String[] header = headerList.get(i);

            List<String[]> detailList = CLMDET.getDetailData(header[0]);
                for(String[] detail : detailList){
                    PaymentDetailDTO paymentDetail = new PaymentDetailDTO();
                    paymentDetail.setTotal(header[2]);
                    paymentDetail.setDiscount(header[1]);
                    paymentDetail.setPatientResponsibility(detail[2]);
                    ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
                    claimResponseDTO.setClaimNumber(header[0]);
                    claimResponseDTO.setDateOfService(formattedDate(detail[1].trim()));
                    claimResponseDTO.setPatientResponsibilityDetails(detail[2]);
                    claimResponseDTO.setClaimType(header[3]);
                    claimResponseDTO.setPaymentDetails(paymentDetail);
                    claimResponseDTO.setPatient(fullName);
                    wholeDTOList.add(claimResponseDTO);

                }
        }
        return ResponseEntity.ok().body(wholeDTOList);
    }

    public static String formattedDate(String processDate) {
        String formattedProcessDate = "";
        try {
            if (processDate.equals("")) {
                return formattedProcessDate;
            }
            if (processDate.length() == 5) {
                processDate = "0" + processDate;
            }
            Date d = new SimpleDateFormat("MMddyy").parse(processDate);
            SimpleDateFormat d2 = new SimpleDateFormat("MM-dd-yyyy");
            formattedProcessDate = d2.format(d).toString();
        } catch (Exception e) {

        }
        return formattedProcessDate;
    }
}
