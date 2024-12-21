package me.tuhin47.auth.payload.common;

import lombok.Value;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Role}
 */
@Value
public class RoleDto implements Serializable {
    Long id;
    @NotBlank
    @Pattern(regexp = "ROLE_.[_A-Z0-9]+", message = "Invalid Role Name. Sample : ROLE_USER")
    String name;
    String description;
    Set<UserDto> users;
    Set<PrivilegeDto> privileges;

    /**
     * DTO for {@link User}
     */
    @Value
    public static class UserDto implements Serializable {
        String id;
    }

    /**
     * DTO for {@link Privilege}
     */
    @Value
    public static class PrivilegeDto implements Serializable {
        Long privilegeId;
    }
}