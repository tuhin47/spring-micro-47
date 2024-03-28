package me.tuhin47.auth.security.oauth2;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class LocalUser extends User implements OidcUser {

    @Serial
    private static final long serialVersionUID = -2845160792248762779L;

    @Id
    private final String userNameOrEmail;
    private final String userId;
    @JsonIgnore
    private final OidcIdToken idToken;
    @JsonIgnore
    private final OidcUserInfo userInfo;

    private Map<String, Object> attributes;

    public LocalUser(final String userNameOrEmail, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, String userId) {
        this(userNameOrEmail, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, null, null, userId);
    }

    public LocalUser(final String userNameOrEmail, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo, String userId) {
        super(userNameOrEmail, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userNameOrEmail = userNameOrEmail != null ? userNameOrEmail : OidcUser.super.getEmail();
        this.idToken = idToken;
        this.userInfo = userInfo;
        this.userId = userId;
    }

    public static LocalUser create(Map<String, Object> attributes, LocalUser localUser) {
        localUser.setAttributes(attributes);
        return localUser;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return userNameOrEmail;
    }

    @Override
    public String getUsername() {
        return userNameOrEmail;
    }

    @Override
    public Map<String, Object> getClaims() {
        return attributes;
    }
}
