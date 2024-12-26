package me.tuhin47.auth.payload.response;

import lombok.Value;
import me.tuhin47.entity.security.SensitiveRead;
import me.tuhin47.utils.RoleUtils;

@Value
public class JwtAuthenticationResponse {

    String accessToken;
    boolean authenticated;
    @SensitiveRead(rolesAllowed = {RoleUtils.ROLE_ADMIN, RoleUtils.ROLE_USER})
    UserInfo user;

}
