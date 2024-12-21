package me.tuhin47.config.redis;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link me.tuhin47.auth.model.User}
 */
@Value
@RedisHash("UserRedis")
public class UserRedis implements Serializable, UserDetails {

    @Id
    String email;
    String userId;
    String displayName;
    String password;
    String provider;
    Set<String> authorityNames;
    String secret;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getSimpleGrantedAuthorities(authorityNames);
    }

    public static List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(Set<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}