package com.login.hth.Claims.Beans;

import com.login.hth.Claims.DTO.ClaimResponseDTO;
import com.login.hth.Claims.DTO.PaymentDetail;
import com.login.hth.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ClaimsData {
    private PaymentDetail paymentDetail;
    public ResponseEntity<Object> checkClaim(String ssn){
        ErrorResponse errorResponse = new ErrorResponse();
        List<String[]> headerList = CLMDET.getHeaderData(ssn);
        List<ClaimResponseDTO> wholeDTOList = new ArrayList<ClaimResponseDTO>();
        List<String[]> insureList = CLMDET.getInsureData(ssn);
        String[] name = Arrays.stream(insureList.get(0)).map(String::trim).toArray(String[]::new);
        String fullName = String.join(" ", name);
        for(int i=0;i<headerList.size();i++){
            String[] header = headerList.get(i);

            List<String[]> detailList = CLMDET.getDetailData(header[0]);
                for(String[] detail : detailList){
                    PaymentDetail paymentDetail = new PaymentDetail();
                    paymentDetail.setTotal(header[2]);
                    paymentDetail.setDiscount(header[1]);
                    paymentDetail.setPatientResponsibility(detail[2]);
                    ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
                    claimResponseDTO.setClaimNumber(header[0]);
                    claimResponseDTO.setDateOfService(detail[1]);
                    claimResponseDTO.setPatientResponsibilityDetails(detail[2]);
                    claimResponseDTO.setPaymentDetails(paymentDetail);
                    claimResponseDTO.setPatient(fullName);
                    wholeDTOList.add(claimResponseDTO);
                }
        }
        return ResponseEntity.accepted().body(wholeDTOList);
    }
}
