package me.tuhin47.auth.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */

@Data
@Builder
public class UserInfo {
    private String id;
    private String displayName, avatar;
    private String email;
    private Set<String> authorityNames;
}
