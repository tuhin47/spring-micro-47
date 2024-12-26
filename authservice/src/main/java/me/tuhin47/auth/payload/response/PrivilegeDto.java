package me.tuhin47.auth.payload.response;

import me.tuhin47.auth.payload.common.RoleIdDto;
import me.tuhin47.auth.payload.common.UserIdDto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link me.tuhin47.auth.model.Privilege}
 */

public record PrivilegeDto(
    Long id,
    String name,
    String description,
    Set<RoleIdDto> roles,
    Set<UserIdDto> users
) implements Serializable {


}