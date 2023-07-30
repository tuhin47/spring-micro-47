package me.tuhin47.payload.response;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */
@Value
@Builder
public class UserResponse implements Serializable {
    String id;
    String email;
    boolean enabled;
    String displayName;
}