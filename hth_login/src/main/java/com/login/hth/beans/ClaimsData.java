package com.login.hth.beans;

import com.login.hth.dto.ClaimResponseDTO;
import com.login.hth.dto.PaymentDetailDTO;
import com.login.hth.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ClaimsData {
    private PaymentDetailDTO paymentDetail;
    public ResponseEntity<Object> checkClaim(String ssn){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
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
