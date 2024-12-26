package me.tuhin47.auth.command;

import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.PrivilegeIdDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Role}
 */

public record RolePayload(
    @NotNull @Pattern(regexp = "ROLE_.[_A-Z0-9]+", message = "Invalid Role Name. Sample : ROLE_USER") String name,
    String description,
    Set<PrivilegeIdDto> privileges
) implements Serializable {

}