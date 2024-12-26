package me.tuhin47.auth.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.auth.payload.constraints.PasswordMatches;
import me.tuhin47.auth.payload.constraints.ValidPassword;
import me.tuhin47.auth.security.oauth2.SocialProvider;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */
@Data
@PasswordMatches
@NoArgsConstructor
public class SignUpRequest {


    private String providerUserId;

    @NotEmpty
    private String displayName;

    @NotEmpty
    @Email
    private String email;

    private SocialProvider socialProvider;

    @NotNull
    @ValidPassword(message = "{Size.userDto.password}")
    private byte[] password;

    @NotNull
    private byte[] matchingPassword;

    private boolean using2FA;

    public void setPassword(@NotBlank String password) {
        this.password = password.getBytes();
    }

    public void setMatchingPassword(@NotBlank String matchingPassword) {
        this.matchingPassword = matchingPassword.getBytes();
    }
}
