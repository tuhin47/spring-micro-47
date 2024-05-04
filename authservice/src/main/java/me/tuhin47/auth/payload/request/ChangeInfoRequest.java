package me.tuhin47.auth.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */
public record ChangeInfoRequest(@Email String email, @NotNull String displayName,
                                @NotNull String password) implements Serializable {
}