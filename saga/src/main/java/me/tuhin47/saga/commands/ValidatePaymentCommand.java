package me.tuhin47.saga.commands;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private long paymentId;
    private long orderId;
//    private CardDetails cardDetails;
}
