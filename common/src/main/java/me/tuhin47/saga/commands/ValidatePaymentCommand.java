package me.tuhin47.saga.commands;


import lombok.Builder;
import lombok.Data;
import me.tuhin47.core.payment.CardDetails;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private CardDetails cardDetails;
}
