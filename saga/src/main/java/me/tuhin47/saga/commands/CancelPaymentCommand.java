package me.tuhin47.saga.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    long paymentId;
    long orderId;
    String paymentStatus = "CANCELLED";
}
