package me.tuhin47.auth.payload.response;

import lombok.Value;

@Value
public class ApiResponse {
    Boolean success;
    String message;
}
