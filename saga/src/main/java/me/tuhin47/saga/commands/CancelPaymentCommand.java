package me.tuhin47.saga.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
public class CancelPaymentCommand extends AbstractCommand<String> {
    
    String paymentId;
    String orderId;
    String paymentStatus = "CANCELLED";
}
