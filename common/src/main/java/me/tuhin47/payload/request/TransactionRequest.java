package me.tuhin47.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import me.tuhin47.core.enums.PaymentMode;

/**
 * DTO for {@link me.tuhin47.paymentservice.model.TransactionDetails}
 */
public record TransactionRequest(
    @NotEmpty String orderId,
    @PositiveOrZero double amount,
    String referenceNumber,
    PaymentMode paymentMode
) {

}
