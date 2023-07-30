package me.tuhin47.auth.util;

import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.security.oauth2.LocalUser;
import me.tuhin47.auth.security.oauth2.SocialProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chinna
 *
 */
public class GeneralUtils {

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public static SocialProvider toSocialProvider(String providerId) {
		for (SocialProvider socialProvider : SocialProvider.values()) {
			if (socialProvider.getProviderType().equals(providerId)) {
				return socialProvider;
			}
		}
		return SocialProvider.LOCAL;
	}

	public static UserInfo buildUserInfo(LocalUser localUser, User user) {
		List<String> roles = localUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//		User user = localUser.getUser();
        return buildUserInfo(user, roles);
	}

    public static UserInfo buildUserInfo(User user, List<String> roles) {
        return UserInfo.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .roles(roles)
                .avatar("https://bootdey.com/img/Content/avatar/avatar1.png")
                .build();
    }
}
