package me.tuhin47.auth.payload.response;

import me.tuhin47.auth.payload.common.PrivilegeIdDto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link me.tuhin47.auth.model.Menu}
 */
public record MenuDto(
    Long id,
    String label,
    Long parentId,
    String icon,
    Set<PrivilegeIdDto> privileges,
    Set<MenuDto> children
) implements Serializable {
}