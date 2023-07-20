package me.tuhin47.saga.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class PaymentCancelledEvent extends AbstractEvent<String>{
    private long paymentId;
    private long orderId;
    private String paymentStatus;
}
