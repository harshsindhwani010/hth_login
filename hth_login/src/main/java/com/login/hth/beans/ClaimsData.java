package com.login.hth.beans;

import com.login.hth.dto.ClaimHeaderDTO;
import com.login.hth.dto.ClaimResponseDTO;
import com.login.hth.dto.MessageDTO;
import com.login.hth.dto.PaymentDetailDTO;
import com.login.hth.utils.ClaimType;
import com.login.hth.utils.Relation2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@Component
public class ClaimsData {
    public ResponseEntity<Object> checkClaim(String ssn, Integer days) throws ParseException {
        LocalDate past = null;
        List dates = new ArrayList();
        if (days != null) {
            SimpleDateFormat formatter;
            for (int i = days; i > 0; i--) {
                past = LocalDate.now().minusDays(i);
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date str = formatter.parse(past.toString());
                formatter = new SimpleDateFormat("Mddyy");
                String date = formatter.format(str);
                dates.add(date);
            }
        }
        List<String[]> data = INSURE.getClmDates(dates, ssn);
        if (data.size() > 0) {
            System.out.println(past);
            List<String[]> headerList = CLMDET.getHeaderData(ssn);
            List<ClaimHeaderDTO> wholeDTOList = new ArrayList<ClaimHeaderDTO>();
            List<String[]> insureList = CLMDET.getInsureData(ssn);
            String[] name = stream(insureList.get(0)).map(String::trim).toArray(String[]::new);
            String fullName = String.join(" ", name);
            List<ClaimResponseDTO> claimDetails = new ArrayList<ClaimResponseDTO>();
            ClaimHeaderDTO claimHeaderDTO = new ClaimHeaderDTO();


            for (int i = 0; i < headerList.size(); i++) {
                String[] header = headerList.get(i);
                List<String[]> detailList = CLMDET.getDetailData(header[0].trim());

                double hCopay = Double.valueOf(header[6].trim());
                double hNotCoverd = Double.valueOf(header[7].trim());
                double hDeducatable = Double.valueOf(header[8].trim());

                claimHeaderDTO.setDateOfService(formatDate(header[2]));
                claimHeaderDTO.setPatient(fullName);
                claimHeaderDTO.setTotalBilled(header[1]);
                claimHeaderDTO.setClaimType(ClaimType.valueOf(header[3].trim()));
                claimHeaderDTO.setPatientRelationship(Relation2.mapper.get(header[5]).trim());
                claimHeaderDTO.setPatientResponsibility(hCopay + hNotCoverd + hDeducatable);

                for (String[] detail : detailList) {
                    double copay = Double.valueOf(detail[3].trim());
                    double notCoverd = Double.valueOf(detail[5].trim());
                    double deducatable = Double.valueOf(detail[6].trim());
                    PaymentDetailDTO paymentDetail = new PaymentDetailDTO();
                    paymentDetail.setTotal(detail[4].trim());
                    paymentDetail.setPlanPaid(detail[3].trim());
                    paymentDetail.setPatientResponsibility(copay + notCoverd + deducatable);
                    ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
                    claimResponseDTO.setClaimNumber(detail[0].trim());
                    claimResponseDTO.setDateOfService(formatDate(detail[2].trim()));
                    claimResponseDTO.setPatientResponsibilityDetails(copay + notCoverd + deducatable);
                    //claimResponseDTO.setClaimType(ClaimType.valueOf(header[3].trim()));
                    //claimResponseDTO.setPatientRelationship(Relation2.mapper.get(header[4].trim()));
                    claimResponseDTO.setPaymentDetails(paymentDetail);
                    claimResponseDTO.setPatient(fullName);
                    claimDetails.add(claimResponseDTO);
                    claimHeaderDTO.setClaimDetails(claimDetails);

                }
                wholeDTOList.add(claimHeaderDTO);
            }
            return ResponseEntity.ok().body(wholeDTOList);
        } else {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessage("No Data Found");
            return ResponseEntity.ok().body(messageDTO);
        }
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
