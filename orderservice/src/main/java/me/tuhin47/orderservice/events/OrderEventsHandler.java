package me.tuhin47.orderservice.events;

import lombok.RequiredArgsConstructor;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.repository.OrderRepository;
import me.tuhin47.saga.events.OrderCancelledEvent;
import me.tuhin47.saga.events.OrderCompletedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventsHandler {

    private final OrderRepository orderRepository;


    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }
}
