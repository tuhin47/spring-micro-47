package com.javachinna.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Value;


@Data
@Builder
public class UserInfo {
	private Long id;
	private String displayName, email, avatar;
	private List<String> roles;

}
