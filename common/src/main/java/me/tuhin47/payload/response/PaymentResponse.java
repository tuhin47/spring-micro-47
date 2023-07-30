package me.tuhin47.payload.response;

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
public class PaymentResponse {

    private String id;
    private String paymentStatus;
    private PaymentMode paymentMode;
    private double amount;
    private Instant paymentDate;
    private String orderId;
}
