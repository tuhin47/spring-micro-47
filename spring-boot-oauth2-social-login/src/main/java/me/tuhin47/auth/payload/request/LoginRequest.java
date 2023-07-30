package me.tuhin47.auth.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String email;

    private byte[] password;

    public void setPassword(String password) {
        this.password = password.getBytes();
    }
}
