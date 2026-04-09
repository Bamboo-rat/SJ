package com.example.authservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;
}
