package me.tuhin47.orderservice.service;

import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponseWithDetails;

public interface OrderService {

    OrderResponseWithDetails getOrderDetails(String orderId);

    Order placeOrderRequest(OrderRequest event);
}
