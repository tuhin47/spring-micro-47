package me.tuhin47.orderservice.service;

import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponse;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(String orderId);

    Order placeOrderRequest(OrderRequest event);
}
