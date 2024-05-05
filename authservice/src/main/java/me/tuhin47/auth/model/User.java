package me.tuhin47.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tuhin47.audit.DateAudit;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * The persistent class for the user database table.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE user set is_deleted=true , deleted_at = now() where id=?")
@Where(clause = "is_deleted = false")
public class User extends DateAudit implements Serializable {

    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "provider_user_id")
    private String providerUserId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "enabled", columnDefinition = "BIT", length = 1)
    private boolean enabled;

    @Column(name = "display_name")
    private String displayName;

    @ToString.Exclude
    @JsonIgnore
    @Column(nullable = false, length = 200)
    private byte[] password;

    private String provider;

    @Column(name = "using_2fa")
    private boolean using2FA;

    private String secret;

    // bi-directional many-to-many association to Role
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    private boolean isDeleted = false;

    private Instant deletedAt;

}
