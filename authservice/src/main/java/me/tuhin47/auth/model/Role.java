package me.tuhin47.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.tuhin47.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the role database table.
 */
@Entity
@NamedEntityGraphs(
    @NamedEntityGraph(name = "Role.withUsersPrivileges", attributeNodes = {@NamedAttributeNode("users"), @NamedAttributeNode("privileges")})
)
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity<Long> implements Serializable {


    @Serial
    private static final long serialVersionUID = 7934821032549942264L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "description", length = 50)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Role{" +
            "description='" + description + '\'' +
            ", id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

}
