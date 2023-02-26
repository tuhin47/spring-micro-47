package com.tuhin47.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {

	private String accessToken;
	private boolean authenticated;
	private UserInfo user;
}
