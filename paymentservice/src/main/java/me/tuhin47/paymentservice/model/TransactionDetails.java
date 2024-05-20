package me.tuhin47.paymentservice.model;

import lombok.*;
import me.tuhin47.entity.audit.UserDateAudit;
import me.tuhin47.core.enums.PaymentMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTION_DETAILS")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails extends UserDateAudit<String> {

    @Id
    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "ORDER_ID", nullable = false, updatable = false)
    private String orderId;

    @Column(name = "MODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode = PaymentMode.CASH;

    @Column(name = "REFERENCE_NUMBER")
    private String referenceNumber;

    @Column(name = "PAYMENT_DATE")
    private Instant paymentDate;

    @Column(name = "STATUS")
    private String paymentStatus;

    @Column(name = "AMOUNT")
    private double amount;
}
