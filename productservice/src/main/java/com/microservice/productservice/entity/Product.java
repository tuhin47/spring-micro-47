package com.microservice.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.audit.UserDateAudit;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends UserDateAudit<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private long price;

    @Column(name = "QUANTITY")
    private long quantity;
}
