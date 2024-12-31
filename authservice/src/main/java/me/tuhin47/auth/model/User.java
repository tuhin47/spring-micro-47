package me.tuhin47.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tuhin47.entity.audit.DateAudit;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE user set is_deleted=true , deleted_at = now() where id=?")
@Where(clause = "is_deleted = false")
@NamedEntityGraph(
    name = "User.withRolesPrivileges",
    attributeNodes = {
        @NamedAttributeNode(value = "roles"),
        @NamedAttributeNode(value = "privileges")
    }
)
public class User extends DateAudit<String> implements Serializable {

    @Serial
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
    @Column(nullable = false, length = 20)
    private String password;

    private String provider;

    @Column(name = "using_2fa")
    private boolean using2FA;

    private String secret;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_privilege",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    @ToString.Exclude
    private Set<Privilege> privileges = new HashSet<>();

    private boolean isDeleted = false;

    private Instant deletedAt;

}
