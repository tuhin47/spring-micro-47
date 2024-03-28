package me.tuhin47.orderservice.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.orderservice.command.CreateOrderCommand;
import me.tuhin47.exception.common.OrderServiceExceptions;
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

        log.info("on() called with: event = [" + event + "]");
        Order order = orderRepository.findById(event.getOrderId())
                                     .orElseThrow(() -> OrderServiceExceptions.ORDER_NOT_FOUND.apply(event.getOrderId()));

        order.setOrderStatus("CREATED");
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        log.info("on() called with: event = [" + event + "]");
        Order order = orderRepository.findById(event.getOrderId())
                                     .orElseThrow(() -> OrderServiceExceptions.ORDER_NOT_FOUND.apply(event.getOrderId()));

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        log.info("on() called with: event = [" + event + "]");
        Order order = orderRepository.findById(event.getOrderId())
                                     .orElseThrow(() -> OrderServiceExceptions.ORDER_NOT_FOUND.apply(event.getOrderId()));

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }
}
