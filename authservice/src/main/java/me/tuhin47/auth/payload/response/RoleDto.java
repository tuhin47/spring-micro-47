package me.tuhin47.auth.payload.response;

import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.PrivilegeIdDto;
import me.tuhin47.auth.payload.common.UserIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Role}
 */

public record RoleDto(
    Long id,
    @NotBlank @Pattern(regexp = "ROLE_.[_A-Z0-9]+", message = "Invalid Role Name. Sample : ROLE_USER") String name,
    String description,
    Set<UserIdDto> users,
    Set<PrivilegeIdDto> privileges
) implements Serializable {


}