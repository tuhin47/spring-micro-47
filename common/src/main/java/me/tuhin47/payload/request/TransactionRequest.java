package me.tuhin47.payload.request;

import me.tuhin47.core.enums.PaymentMode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

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
