package me.tuhin47.paymentservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tuhin47.core.enums.PaymentMode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    @NotEmpty
    private String orderId;
    @PositiveOrZero
    private double amount;
    private String referenceNumber;
    private PaymentMode paymentMode;

}
