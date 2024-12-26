package me.tuhin47.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tuhin47.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(
    name = "Privilege.withUsersRoles",
    attributeNodes = {@NamedAttributeNode(value = "users"), @NamedAttributeNode(value = "roles")}
)
@ToString
public class Privilege extends BaseEntity<Long> implements Serializable {


    @Serial
    private static final long serialVersionUID = 917893631751753550L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRIVILEGE_ID")
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "privileges")
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "privileges")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
}
