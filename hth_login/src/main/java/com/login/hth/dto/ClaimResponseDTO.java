package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimResponseDTO {

    private String dateOfService;
    private String patient;
    private String claimNumber;
    private PaymentDetailDTO paymentDetails;
    private String patientResponsibilityDetails;

}
