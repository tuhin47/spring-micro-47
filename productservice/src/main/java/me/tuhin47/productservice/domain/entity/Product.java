package me.tuhin47.productservice.domain.entity;

import lombok.*;
import me.tuhin47.entity.audit.UserDateAudit;
import me.tuhin47.productservice.domain.enums.ProductType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serial;

@Entity
@NamedQueries({
    @NamedQuery(name = "Product.countByProductType", query = "select p.productType as productType, count(p) as count from Product p group by p.productType order by p.productType", lockMode = LockModeType.READ),
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends UserDateAudit<String> {

    @Serial
    private static final long serialVersionUID = 9153625892766185972L;
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @PositiveOrZero
    @Column(name = "price", nullable = false)
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double price;

    @PositiveOrZero
    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", length = 50)
    private ProductType productType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "description", length = 250)
    private String description;

}
