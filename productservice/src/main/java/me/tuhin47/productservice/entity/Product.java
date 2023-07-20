package me.tuhin47.productservice.entity;

import lombok.*;
import me.tuhin47.audit.UserDateAudit;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends UserDateAudit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id = UUID.randomUUID().toString();

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private long price;

    @Column(name = "QUANTITY")
    private long quantity;
}
