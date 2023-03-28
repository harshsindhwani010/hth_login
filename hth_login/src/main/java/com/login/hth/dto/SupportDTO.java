package com.login.hth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component

public class SupportDTO {
    @Value("${spring.demo.address}")
    private String address;
    @Value("${spring.demo.email}")
    private String email;
    @Value("${spring.demo.phone}")
    private String phone;
    @Value("${spring.demo.location}")
    private String location;
    @Value("${spring.demo.latitude}")
    private double latitude ;
    @Value("${spring.demo.longitude}")
    private double longitude ;

}

