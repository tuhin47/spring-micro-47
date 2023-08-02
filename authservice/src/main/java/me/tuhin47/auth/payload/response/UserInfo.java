package me.tuhin47.auth.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class UserInfo {
    private String id;
    private String displayName, email, avatar;
    private List<String> roles;
}
