package me.tuhin47.auth.command;

import javax.validation.constraints.NotBlank;

public record PrivilegePayload(
    @NotBlank String name,
    String description, Long parentId) {

}