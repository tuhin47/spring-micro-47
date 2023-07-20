package me.tuhin47.orderservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.orderservice.utils.PaymentMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private String productId;
    private long totalAmount;
    private long quantity;
    private PaymentMode paymentMode;
}
