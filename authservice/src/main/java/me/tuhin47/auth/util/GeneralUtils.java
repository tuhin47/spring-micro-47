package me.tuhin47.auth.util;

import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.security.oauth2.SocialProvider;
import me.tuhin47.config.redis.UserRedis;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Chinna
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

    public static UserInfo buildUserInfo(UserRedis user) {
        return UserInfo.builder()
                       .id(user.getUserId())
                       .displayName(user.getDisplayName())
                       .email(user.getEmail())
                       .authorityNames(user.getAuthorityNames())
                       .avatar("https://bootdey.com/img/Content/avatar/avatar1.png")
                       .build();
    }

    public static Set<String> getUserAuthorities(User user) {
        return Stream.concat(
            user.getRoles().stream()
                .flatMap(role -> Stream.concat(
                    role.getPrivileges().stream().map(Privilege::getName),
                    Stream.of(role.getName())
                )),

            user.getPrivileges().stream().map(Privilege::getName)
        ).collect(Collectors.toSet());
    }
}
