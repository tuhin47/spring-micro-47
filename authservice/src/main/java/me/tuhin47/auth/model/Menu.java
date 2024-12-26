package me.tuhin47.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tuhin47.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Menu extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7294938675745170742L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String label;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "parent")
    private Menu parent;

    @Column(name = "icon", length = 20)
    private String icon;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private Set<Menu> children;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "menu_privileges",
        joinColumns = @JoinColumn(name = "menu_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges = new LinkedHashSet<>();


}
