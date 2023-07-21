package me.tuhin47.paymentservice.aggregate;

import lombok.extern.slf4j.Slf4j;
import me.tuhin47.saga.commands.CancelPaymentCommand;
import me.tuhin47.saga.commands.ValidatePaymentCommand;
import me.tuhin47.saga.events.PaymentCancelledEvent;
import me.tuhin47.saga.events.PaymentProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        //Validate the Payment Details
        // Publish the Payment Processed event
        log.info("Executing ValidatePaymentCommand for Order Id: {} and Payment Id: {}", validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());

//        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(validatePaymentCommand.getPaymentId(), validatePaymentCommand.getOrderId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();

        BeanUtils.copyProperties(validatePaymentCommand, paymentProcessedEvent);

        AggregateLifecycle.apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent Applied. validatePaymentCommand {}", validatePaymentCommand);
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }

    @CommandHandler
    public void handle(CancelPaymentCommand cancelPaymentCommand) {
        log.info("handle() called with: cancelPaymentCommand = [" + cancelPaymentCommand + "]");
        PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();

        BeanUtils.copyProperties(cancelPaymentCommand, paymentCancelledEvent);

        AggregateLifecycle.apply(paymentCancelledEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent event) {
        log.info("on() called with: event = [" + event + "]");
        this.paymentStatus = event.getPaymentStatus();
    }
}
