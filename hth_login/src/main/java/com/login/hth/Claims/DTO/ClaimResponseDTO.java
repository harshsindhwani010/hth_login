package com.login.hth.Claims.DTO;

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
    private PaymentDetail paymentDetails;
    private String patientResponsibilityDetails;

}
