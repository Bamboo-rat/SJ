package com.example.authservice.dto.request;

import com.example.authservice.entity.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegisterRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private String password;
    private String confirmPassword;
}
