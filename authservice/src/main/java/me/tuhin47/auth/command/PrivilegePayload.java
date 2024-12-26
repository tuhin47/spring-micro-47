package me.tuhin47.auth.command;

/**
 * DTO for {@link me.tuhin47.auth.model.Privilege}
 */
public record PrivilegePayload(
    String name,
    String description) {
}