package com.example.authservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpVerificationRequest {
    private String phoneNumber;
    private String otpCode;
}
