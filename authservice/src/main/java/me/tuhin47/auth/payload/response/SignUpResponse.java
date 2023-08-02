package me.tuhin47.auth.payload.response;

import lombok.Value;

@Value
public class SignUpResponse {
    boolean using2FA;
    String qrCodeImage;
}
