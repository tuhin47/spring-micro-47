package me.tuhin47.auth.command;

import me.tuhin47.auth.model.Menu;
import me.tuhin47.auth.payload.common.MenuIdDto;
import me.tuhin47.auth.payload.common.PrivilegeIdDto;

import java.util.Set;

/**
 * DTO for {@link Menu}
 */
public record MenuPayload(
    String label,
    String icon,
    MenuIdDto parent,
    Set<PrivilegeIdDto> privileges) {
}