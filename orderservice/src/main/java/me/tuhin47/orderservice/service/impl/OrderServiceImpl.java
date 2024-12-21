package me.tuhin47.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.client.PaymentService;
import me.tuhin47.client.ProductService;
import me.tuhin47.client.UserService;
import me.tuhin47.config.exception.common.OrderServiceExceptions;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.mapper.OrderMapper;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponseWithDetails;
import me.tuhin47.orderservice.repository.OrderRepository;
import me.tuhin47.orderservice.service.OrderService;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.payload.response.TopOrderDto;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final UserService userService;


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

    @Override
    public List<TopOrderDto> getTop10OrderByDateRange(Instant orderDateStart, Instant orderDateEnd) {
        List<TopOrderDto> approved = orderRepository.getTopOrderByDateRange(orderDateStart, orderDateEnd, "APPROVED", PageRequest.of(0, 10))
                                                    .getContent();

        String[] ids = approved.stream().map(TopOrderDto::getBuyerId).toList().toArray(new String[0]);
        Map<String, UserResponse> collect = Objects.requireNonNull(userService.getAllUsers(ids)
                                                                              .getBody())
                                                   .stream()
                                                   .collect(Collectors.toMap(UserResponse::getId, Function.identity()));

        approved.forEach(
            topOrderDto -> {
                UserResponse userResponse = collect.get(topOrderDto.getBuyerId());
                topOrderDto.setUserResponse(userResponse);
            }
        );

        return approved;
    }
}
