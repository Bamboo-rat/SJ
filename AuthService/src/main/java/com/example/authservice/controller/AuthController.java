package com.example.authservice.controller;

import com.example.authservice.dto.request.LoginRequest;
import com.example.authservice.dto.response.LoginResponse;
import com.example.common.dto.ApiResponse;
import com.example.common.handler.HandlerContainer;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final HandlerContainer handlerContainer;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = handlerContainer.handle(request);
        return ApiResponse.<LoginResponse>builder()
            .status(200)
            .message("OK")
            .data(response)
            .timestamp(LocalDateTime.now())
                .build();
    }
}
