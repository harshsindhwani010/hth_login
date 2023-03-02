package com.login.hth.beans;

import com.login.hth.dto.ClaimResponseDTO;
import com.login.hth.dto.PaymentDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Parser;
import java.text.SimpleDateFormat;
import java.util.*;

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

                    double copay       = Double.valueOf(detail[2].trim());
                    double notCoverd   = Double.valueOf(detail[4]);;
                    double deducatable = Double.valueOf(detail[5].trim());

                    PaymentDetailDTO paymentDetail = new PaymentDetailDTO();
                    paymentDetail.setTotal(header[1]);
                    paymentDetail.setPlanPaid(detail[3]);
                    paymentDetail.setPatientResponsibility(copay+notCoverd+deducatable);
                    ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
                    claimResponseDTO.setClaimNumber(header[0]);
                    claimResponseDTO.setDateOfService(formatDate(detail[1].trim()));
                    claimResponseDTO.setPatientResponsibilityDetails(copay+notCoverd+deducatable);
                    claimResponseDTO.setClaimType(header[2]);
                    claimResponseDTO.setPatientRelatipnship(header[4]);
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
            Date d = new SimpleDateFormat("MMdyyyy").parse(processDate);
            SimpleDateFormat d2 = new SimpleDateFormat("MM/dd/yyyy");
            formattedProcessDate = d2.format(d).toString();
        } catch (Exception e) {

        }
        return formattedProcessDate;
    }
    public static String formatDate(String processDate) {
        String formattedProcessDate = "";
        try {
            if (processDate.equals("")) {
                return formattedProcessDate;
            }
            if (processDate.length() == 5) {
                processDate = "0" + processDate;
            }
            Date d = new SimpleDateFormat("MMddyy", Locale.ENGLISH).parse(processDate);
            SimpleDateFormat d2 = new SimpleDateFormat("MMM dd,yyyy");
            formattedProcessDate = d2.format(d).toString();
        } catch (Exception e) {

        }
        return formattedProcessDate;
}
}
