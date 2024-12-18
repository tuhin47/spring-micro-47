package me.tuhin47.auth.payload.request;

import lombok.Data;
import me.tuhin47.auth.payload.constraints.ValidPassword;
import me.tuhin47.auth.security.oauth2.SocialProvider;
import me.tuhin47.auth.validator.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Data
@PasswordMatches
public class SignUpRequest {

    private String userID;

    private String providerUserId;

    @NotEmpty
    private String displayName;

    @NotEmpty
    @Email
    private String email;

    private SocialProvider socialProvider = SocialProvider.LOCAL;

    @ValidPassword(message = "{Size.userDto.password}")
    private CharSequence password;

    @NotEmpty
    private CharSequence matchingPassword;

    private boolean using2FA;

    public SignUpRequest(String providerUserId, String displayName, String email, String password, SocialProvider socialProvider) {
        this.providerUserId = providerUserId;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String providerUserID;
        private String displayName;
        private String email;
        private String password;
        private SocialProvider socialProvider;

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addDisplayName(final String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder addEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder addSocialProvider(final SocialProvider socialProvider) {
            this.socialProvider = socialProvider;
            return this;
        }

        public SignUpRequest build() {
            return new SignUpRequest(providerUserID, displayName, email, password, socialProvider);
        }
    }
}
