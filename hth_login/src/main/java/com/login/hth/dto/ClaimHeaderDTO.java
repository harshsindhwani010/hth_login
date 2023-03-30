package com.login.hth.dto;

import com.login.hth.utils.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClaimHeaderDTO {

    private String dateOfService;
    private String patient;
    private String totalBilled;
    private ClaimType claimType;
    private String patientRelationship;
    private double patientResponsibility;
    private List<ClaimResponseDTO> claimDetails;


    public String getClaimType() {
        return claimType.label;
    }

}
