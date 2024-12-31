package me.tuhin47.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
