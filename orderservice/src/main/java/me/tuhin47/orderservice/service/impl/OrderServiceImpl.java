package me.tuhin47.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.orderservice.exception.OrderServiceExceptions;
import me.tuhin47.orderservice.external.client.PaymentService;
import me.tuhin47.orderservice.external.client.ProductService;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.mapper.OrderMapper;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponseWithDetails;
import me.tuhin47.orderservice.repository.OrderRepository;
import me.tuhin47.orderservice.service.OrderService;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.payload.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;


    @Override
    public OrderResponseWithDetails getOrderDetails(String orderId) {

        Order order = orderRepository.findById(orderId)
                             .orElseThrow(() -> OrderServiceExceptions.ORDER_NOT_FOUND.apply(orderId));

        ProductResponse productResponse = productService.getProductById(order.getProductId()).getBody();

        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(order.getId()).getBody();

        return orderMapper.toDtoWithDetails(order, productResponse, paymentResponse);

    }

    @Override
    public Order placeOrderRequest(OrderRequest orderRequest) {
        log.info("OrderServiceImpl | placeOrder | Creating Order with Status CREATED");
        Order order = Order.builder()
                           .amount(orderRequest.getTotalAmount())
                           .orderStatus("CREATED")
                           .productId(orderRequest.getProductId())
                           .orderDate(Instant.now())
                           .quantity(orderRequest.getQuantity())
                           .build();
        orderRepository.save(order);
        return order;
    }
}
