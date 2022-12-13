package com.login.hth.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private String status;
    private String error;
}
