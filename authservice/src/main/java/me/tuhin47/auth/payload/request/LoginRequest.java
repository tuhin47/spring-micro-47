package me.tuhin47.auth.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotBlank
    private String email;

    @NotNull
    private byte[] password;

    public void setPassword(String password) {
        if (password != null) {
            this.password = password.getBytes();
        }
    }
}
