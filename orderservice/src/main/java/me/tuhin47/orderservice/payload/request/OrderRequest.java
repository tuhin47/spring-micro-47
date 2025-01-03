package me.tuhin47.orderservice.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.core.enums.PaymentMode;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty
    private String productId;
    private double totalAmount;
    private long quantity;
    private PaymentMode paymentMode;
}
