package me.tuhin47.auth.payload.response;

import lombok.Builder;
import lombok.Data;
import me.tuhin47.entity.security.SensitiveRead;

import java.util.Set;


@Data
@Builder
public class UserInfo {
    private String id;
    private String displayName, avatar;
    @SensitiveRead(rolesAllowed = {"ROLE_ADMIN"})
    private String email;
    private Set<String> roles;
}
