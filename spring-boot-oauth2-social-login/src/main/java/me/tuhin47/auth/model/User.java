package me.tuhin47.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tuhin47.audit.DateAudit;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * The persistent class for the user database table.
 *
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User extends DateAudit implements Serializable {

	private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

	@Column(name = "PROVIDER_USER_ID")
	private String providerUserId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	private boolean enabled;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

    @ToString.Exclude
    @JsonIgnore
    @Column(nullable = false, length = 200)
	private byte[] password;

	private String provider;

	@Column(name = "USING_2FA")
	private boolean using2FA;

	private String secret;

	// bi-directional many-to-many association to Role
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
	private Set<Role> roles;
}
