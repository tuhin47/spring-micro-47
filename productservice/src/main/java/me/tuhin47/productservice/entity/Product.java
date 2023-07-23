package me.tuhin47.productservice.entity;

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
    @Column(nullable = false, updatable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @PositiveOrZero
    @Column(name = "PRICE", nullable = false)
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double price;

    @PositiveOrZero
    @Column(name = "QUANTITY", nullable = false)
    private long quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

}
