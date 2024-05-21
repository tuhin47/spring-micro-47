package me.tuhin47.auth.payload.response;

import lombok.Value;
import me.tuhin47.entity.security.SensitiveRead;

@Value
public class JwtAuthenticationResponse {

    String accessToken;
    boolean authenticated;
    @SensitiveRead(rolesAllowed = {"ROLE_ADMIN"})
    UserInfo user;
}
