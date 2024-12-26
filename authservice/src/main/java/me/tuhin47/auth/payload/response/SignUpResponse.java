package me.tuhin47.auth.payload.response;

public record SignUpResponse(boolean using2FA, String qrCodeImage, UserInfo userInfo) {
}
