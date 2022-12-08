package com.login.hth.user.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private String status;
    private String error;
}
