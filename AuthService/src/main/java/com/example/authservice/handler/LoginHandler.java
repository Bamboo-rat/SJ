package com.example.authservice.handler;

import com.example.authservice.dto.request.LoginRequest;
import com.example.authservice.dto.response.LoginResponse;
import com.example.authservice.service.AuthenticationService;
import com.example.common.handler.BaseHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginHandler implements BaseHandler<LoginRequest, LoginResponse> {
    private final AuthenticationService authenticationService;

    public LoginHandler(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public LoginResponse execute(LoginRequest request) {
        return authenticationService.authenticate(request);
    }

    @Override
    public Class<LoginRequest> getRequestType() {
        return LoginRequest.class;
    }
}
