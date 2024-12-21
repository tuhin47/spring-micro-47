package me.tuhin47.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * The persistent class for the role database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "description", length = 50)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role role = (Role) obj;
        return role.equals(role.name);
    }

    @Override
    public String toString() {
        return "Role [name=" + name + "]" + "[id=" + roleId + "]";
    }
}
