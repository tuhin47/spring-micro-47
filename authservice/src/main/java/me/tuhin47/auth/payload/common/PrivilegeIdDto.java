package me.tuhin47.auth.payload.common;

import me.tuhin47.auth.model.Privilege;

import java.io.Serializable;

/**
 * DTO for {@link Privilege}
 */
public record PrivilegeIdDto(long id) implements Serializable {
}