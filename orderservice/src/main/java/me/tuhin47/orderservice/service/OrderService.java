package me.tuhin47.orderservice.service;

import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponseWithDetails;
import me.tuhin47.payload.response.TopOrderDto;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;

public interface OrderService {

    OrderResponseWithDetails getOrderDetails(String orderId);

    Order placeOrderRequest(OrderRequest event);


    List<TopOrderDto> getTop10OrderByDateRange(Instant orderDateStart, Instant orderDateEnd);
}
