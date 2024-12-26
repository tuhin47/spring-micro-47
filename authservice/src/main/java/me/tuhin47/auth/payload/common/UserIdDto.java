package me.tuhin47.auth.payload.common;

import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */
public record UserIdDto(String id) implements Serializable {
}
