package me.tuhin47.auth.security.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;

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

    @Setter
    private Map<String, Object> attributes;

    public LocalUser(me.tuhin47.auth.model.User user, final Collection<? extends GrantedAuthority> authorities) {
        this(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, authorities, null, null, user.getId());
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
