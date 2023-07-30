package me.tuhin47.orderservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.core.enums.PaymentMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private String orderId;
    private double amount;
    private String referenceNumber;
    private PaymentMode paymentMode;

}
