package me.tuhin47.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Privilege implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRIVILEGE_ID")
    private Long privilegeId;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

    @ManyToMany(mappedBy = "privileges")
    private Set<User> users;

    public Privilege(Long privilegeId) {
        this.privilegeId = privilegeId;
    }
}
