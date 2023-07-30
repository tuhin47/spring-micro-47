package me.tuhin47.auth.payload.response;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {

	String accessToken;
	boolean authenticated;
	UserInfo user;
}
