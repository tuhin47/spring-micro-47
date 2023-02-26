package com.tuhin47.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserInfo {
	private Long id;
	private String displayName, email, avatar;
	private List<String> roles;

}
