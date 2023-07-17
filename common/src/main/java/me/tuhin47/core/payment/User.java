package me.tuhin47.core.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private CardDetails cardDetails;
}
