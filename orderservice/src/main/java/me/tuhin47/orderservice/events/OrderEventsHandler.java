package me.tuhin47.orderservice.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.exception.common.OrderServiceExceptions;
import me.tuhin47.orderservice.command.CreateOrderCommand;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.repository.OrderRepository;
import me.tuhin47.saga.events.OrderCancelledEvent;
import me.tuhin47.saga.events.OrderCompletedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventsHandler {

    private final OrderRepository orderRepository;

    @EventHandler
    public void on(CreateOrderCommand event) {
        saveOrder(event, event.getOrderId(), "CREATED");
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        saveOrder(event, event.getOrderId(), event.getOrderStatus());
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        saveOrder(event, event.getOrderId(), event.getOrderStatus());
    }

    private void saveOrder(Object event, String id, String status) {
        log.info("on() called with: event = [{}]", event);
        Order order = orderRepository.findById(id)
                                     .orElseThrow(() -> OrderServiceExceptions.ORDER_NOT_FOUND.apply(id));

        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}