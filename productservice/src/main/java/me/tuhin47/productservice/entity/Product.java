package me.tuhin47.productservice.entity;

import lombok.*;
import me.tuhin47.audit.UserDateAudit;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends UserDateAudit<String> {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private long price;

    @Column(name = "QUANTITY")
    private long quantity;
}
