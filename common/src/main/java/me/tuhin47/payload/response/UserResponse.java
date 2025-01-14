package me.tuhin47.payload.response;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */
@Value
@Builder
public class UserResponse implements BaseResponse {
    String id;
    String email;
    boolean enabled;
    String displayName;
}