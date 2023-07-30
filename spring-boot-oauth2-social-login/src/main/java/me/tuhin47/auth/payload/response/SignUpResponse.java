package me.tuhin47.auth.payload.response;

import lombok.Value;

@Value
public class SignUpResponse {
	private boolean using2FA;
	private String qrCodeImage;
}
