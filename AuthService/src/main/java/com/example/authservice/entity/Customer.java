package com.example.authservice.entity;

import com.example.authservice.entity.enums.Gender;
import com.example.authservice.entity.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity(name = "customer")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @UuidGenerator
    private String id;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String username;
    private String password;
    @Builder.Default
    private Roles role = Roles.CUSTOMER;

}
