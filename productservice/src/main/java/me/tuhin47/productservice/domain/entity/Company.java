package me.tuhin47.productservice.domain.entity;

import lombok.*;
import me.tuhin47.audit.UserDateAudit;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends UserDateAudit<String> {

    @Id
    @Column(nullable = false, updatable = false, length = 36)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String address;

    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();
}
