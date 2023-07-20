package me.tuhin47.orderservice.saga;

import lombok.extern.slf4j.Slf4j;
import me.tuhin47.core.payment.UserResponse;
import me.tuhin47.orderservice.events.OrderCreatedEvent;
import me.tuhin47.saga.commands.CancelOrderCommand;
import me.tuhin47.saga.commands.CancelPaymentCommand;
import me.tuhin47.saga.commands.CompleteOrderCommand;
import me.tuhin47.saga.commands.ValidatePaymentCommand;
import me.tuhin47.saga.events.OrderCancelledEvent;
import me.tuhin47.saga.events.OrderCompletedEvent;
import me.tuhin47.saga.events.PaymentCancelledEvent;
import me.tuhin47.saga.events.PaymentProcessedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;


    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for Order Id : {}", event.getOrderId());

        //GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());

        UserResponse user = null;

        try {
            // user = queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(UserResponse.class)).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            //Start the Compensating transaction
            cancelOrderCommand(event.getId(), event.getOrderId());
        }

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand
                .builder()
//                .cardDetails(user.getCardDetails())
                .id(UUID.randomUUID().toString())
                .orderId(event.getOrderId())
                .build();

        log.info( "handle() called with: validatePaymentCommand = [" + validatePaymentCommand + "]");

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    public void cancelOrderCommand(String eventId, long orderId) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .id(eventId)
                .orderId(orderId)
                .build();
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent in Saga for Order Id : {}", event.getOrderId());

        try {

            CompleteOrderCommand completeOrderCommand
                    = CompleteOrderCommand.builder()
                    .id(event.getId())
                    .orderId(event.getOrderId())
                    .orderStatus("APPROVED")
                    .build();

            log.info("handle() called with: CompleteOrderCommand = [" + completeOrderCommand + "]");
            commandGateway.send(completeOrderCommand);
          /*  if (true)
                throw new Exception();

            ShipOrderCommand shipOrderCommand
                    = ShipOrderCommand
                    .builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();
            commandGateway.send(shipOrderCommand);*/
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
            cancelPaymentCommand(event);
        }

    }

    public void cancelPaymentCommand(PaymentProcessedEvent event) {
        log.info("cancelPaymentCommand() called with: event = [" + event + "]");
        CancelPaymentCommand cancelPaymentCommand = CancelPaymentCommand.builder().id(event.getId()).paymentId(event.getPaymentId()).orderId(event.getOrderId()).build();
        commandGateway.send(cancelPaymentCommand);
    }

    /*@SagaEventHandler(associationProperty = "id)
    public void handle(OrderShippedEvent event) {

        log.info("OrderShippedEvent in Saga for Order Id : {}",
                event.getOrderId());

        CompleteOrderCommand completeOrderCommand
                = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .orderStatus("APPROVED")
                .build();

        commandGateway.send(completeOrderCommand);
    }*/

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent in Saga for Order Id : {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(OrderCancelledEvent event) {
        log.info("OrderCancelledEvent in Saga for Order Id : {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "id")
    public void handle(PaymentCancelledEvent event) {
        log.info("PaymentCancelledEvent in Saga for Order Id : {}", event.getOrderId());
        cancelOrderCommand(event.getId(), event.getOrderId());
    }
}
