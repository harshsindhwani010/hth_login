package com.login.hth.beans;

import com.login.hth.dto.ClaimResponseDTO;
import com.login.hth.dto.PaymentDetailDTO;
import com.login.hth.utils.ClaimType;
import com.login.hth.utils.RelationType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ClaimsData {
    public ResponseEntity<Object> checkClaim(String ssn) {
        List<String[]> headerList = CLMDET.getHeaderData(ssn);
        List<ClaimResponseDTO> wholeDTOList = new ArrayList<ClaimResponseDTO>();
        List<String[]> insureList = CLMDET.getInsureData(ssn);
        String[] name = Arrays.stream(insureList.get(0)).map(String::trim).toArray(String[]::new);
        String fullName = String.join(" ", name);
        for (int i = 0; i < headerList.size(); i++) {
            String[] header = headerList.get(i);
            List<String[]> detailList = CLMDET.getDetailData(header[0].trim());

            for (String[] detail : detailList) {
                double copay = Double.valueOf(detail[2].trim());
                double notCoverd = Double.valueOf(detail[4].trim());
                ;
                double deducatable = Double.valueOf(detail[5].trim());
                PaymentDetailDTO paymentDetail = new PaymentDetailDTO();
                paymentDetail.setTotal(header[1].trim());
                paymentDetail.setPlanPaid(detail[3].trim());
                paymentDetail.setPatientResponsibility(copay + notCoverd + deducatable);
                ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
                claimResponseDTO.setClaimNumber(header[0].trim());
                claimResponseDTO.setDateOfService(formatDate(detail[1].trim()));
                claimResponseDTO.setPatientResponsibilityDetails(copay + notCoverd + deducatable);
                claimResponseDTO.setClaimType(ClaimType.valueOf(header[2].trim()));
                claimResponseDTO.setPatientRelationship(RelationType.valueOf(header[4].trim()));
                claimResponseDTO.setPaymentDetails(paymentDetail);
                claimResponseDTO.setPatient(fullName);
                wholeDTOList.add(claimResponseDTO);
            }
        }
        return ResponseEntity.ok().body(wholeDTOList);
    }

    public static String formattedDate(String processDate) {
        String formattedProcessDate = "";
//        String formattedProcessDate1 = "MM/dd/yyyy";
        try {
            if (processDate.equals("")) {
                return formattedProcessDate;
            }
            if (processDate.length() == 7 || processDate.length() == 5) {
                processDate = "0" + processDate;
            }
            if (processDate.length() == 8) {
                Date d = new SimpleDateFormat("MMddyyyy", Locale.ENGLISH).parse(processDate);
              //  Date e = new SimpleDateFormat("MMddyyyy", Locale.ENGLISH).parse(processDate);
                SimpleDateFormat d2 = new SimpleDateFormat("MM/dd/yyyy");


                formattedProcessDate = d2.format(d).toString();
            } else {
                Date d = new SimpleDateFormat("MMddyy", Locale.ENGLISH).parse(processDate);
                //Date e = new SimpleDateFormat("MMddyyyy", Locale.ENGLISH).parse(processDate);
                SimpleDateFormat d2 = new SimpleDateFormat("MM/dd/yyyy");


                formattedProcessDate = d2.format(d).toString();
            }
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
            if (processDate.length() == 5 || processDate.length() == 7) {
                processDate = "0" + processDate;
            }
            Date d = new SimpleDateFormat("MMddyy", Locale.ENGLISH).parse(processDate);
            SimpleDateFormat d2 = new SimpleDateFormat("MMM dd,yyyy");
            formattedProcessDate = d2.format(d).toString();
        } catch (Exception e) {

        }
        return formattedProcessDate;
    }
    public static String formatDates(String processDate) {
        String formattedProcessDate = "";
        try {
            if (processDate.equals("")) {
                return formattedProcessDate;
            }
            if (processDate.length() == 5 || processDate.length() == 7) {
                processDate = "0" + processDate;
            }
            Date d = new SimpleDateFormat("MMddyy", Locale.ENGLISH).parse(processDate);
            SimpleDateFormat d2 = new SimpleDateFormat("MMMM dd,yyyy");
            formattedProcessDate = d2.format(d).toString();
        } catch (Exception e) {

        }
        return formattedProcessDate;
    }
}
