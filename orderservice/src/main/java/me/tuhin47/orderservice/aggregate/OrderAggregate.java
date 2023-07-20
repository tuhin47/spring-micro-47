package me.tuhin47.orderservice.aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.orderservice.command.CreateOrderCommand;
import me.tuhin47.orderservice.events.OrderCreatedEvent;
import me.tuhin47.saga.commands.CancelOrderCommand;
import me.tuhin47.saga.commands.CompleteOrderCommand;
import me.tuhin47.saga.events.OrderCancelledEvent;
import me.tuhin47.saga.events.OrderCompletedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String id;
    private long orderId;
    private long productId;
    private long userId;
//    private String addressId;
    private long quantity;
    private String orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //Validate The Command
        log.info("OrderAggregate() called with: createOrderCommand = [" + createOrderCommand + "]");

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        log.info("OrderAggregate() with: OrderCreatedEvent = [" + orderCreatedEvent + "]");

        AggregateLifecycle.apply(orderCreatedEvent);
    }


    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("on() called with: event = [" + event + "]");

        this.id = event.getId();
        this.orderStatus = event.getOrderStatus();
        this.userId = event.getUserId();
        this.orderId = event.getOrderId();
        this.quantity = event.getQuantity();
        this.productId = event.getProductId();
//        this.addressId = event.getAddressId();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        //Validate The Command
        // Publish Order Completed Event
        log.info("handle() called with: completeOrderCommand = [" + completeOrderCommand + "]");
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .id(completeOrderCommand.getId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .orderId(completeOrderCommand.getOrderId())
                .build();

        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        log.info("on() called with: event = [" + event + "]");
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {
        log.info("handle() called with: cancelOrderCommand = [" + cancelOrderCommand + "]");
        OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
        BeanUtils.copyProperties(cancelOrderCommand, orderCancelledEvent);
        AggregateLifecycle.apply(orderCancelledEvent);
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        log.info("on() called with: event = [" + event + "]");
        this.orderStatus = event.getOrderStatus();
    }
}
