package com.example.authservice.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {

	private final String token;
	private final String tokenType;

	public LoginResponse(String token) {
		this.token = token;
		this.tokenType = "Bearer";
	}
}
