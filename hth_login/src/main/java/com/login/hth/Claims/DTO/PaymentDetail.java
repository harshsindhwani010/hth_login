package com.login.hth.Claims.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDetail {
    private String total;
    private String discount;
    private String patientResponsibility;


}
