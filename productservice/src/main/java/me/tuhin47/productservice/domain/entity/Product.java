package me.tuhin47.productservice.domain.entity;

import lombok.*;
import me.tuhin47.audit.UserDateAudit;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends UserDateAudit<String> {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 50)
    private String productName;

    @PositiveOrZero
    @Column(name = "PRICE", nullable = false)
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double price;

    @PositiveOrZero
    @Column(name = "QUANTITY", nullable = false)
    private long quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "description", length = 250)
    private String description;

}
