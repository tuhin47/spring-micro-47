package me.tuhin47.saga.commands;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
public class ValidatePaymentCommand extends AbstractCommand<String>{

    private long paymentId;
    private long orderId;
//    private CardDetails cardDetails;
}
