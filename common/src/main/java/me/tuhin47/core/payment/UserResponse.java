package me.tuhin47.core.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private long userId;
    private String firstName;
    private String lastName;
    private CardDetails cardDetails;
}
