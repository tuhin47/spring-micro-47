package me.tuhin47.auth.dto;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
}
