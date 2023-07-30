package me.tuhin47.orderservice.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.core.enums.PaymentMode;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetails {
    private long paymentId;
    private PaymentMode paymentMode;
    private String paymentStatus;
    private Instant paymentDate;
}
