package com.login.hth.dto;


import com.login.hth.utils.ClaimType;
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
    private ClaimType claimType;
    private String patientRelationship;
    //private RelationType patientRelationship;
    private PaymentDetailDTO paymentDetails;
    private double patientResponsibilityDetails;


    public String getClaimType() {
        return claimType.label;
    }

//    public String getPatientRelationship() {
//        return patientRelationship.label;
//    }
}
