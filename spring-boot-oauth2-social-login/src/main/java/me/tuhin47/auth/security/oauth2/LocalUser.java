package me.tuhin47.auth.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * @author Chinna
 */
public class LocalUser extends User implements OAuth2User, OidcUser {

    private static final long serialVersionUID = -2845160792248762779L;
    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;
    private final String email;
    private Map<String, Object> attributes;

    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, String email) {
        this(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, null, null, email);
    }

    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
                     OidcUserInfo userInfo, String email) {
        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email != null ? email : OidcUser.super.getEmail();
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public static LocalUser create(Map<String, Object> attributes, LocalUser localUser) {
        localUser.setAttributes(attributes);
        return localUser;
    }

    @Override
    public String getName() {
        return this.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}
