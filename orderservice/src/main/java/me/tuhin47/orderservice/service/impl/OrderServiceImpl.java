package me.tuhin47.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.orderservice.exception.CustomException;
import me.tuhin47.orderservice.external.client.PaymentService;
import me.tuhin47.orderservice.external.client.ProductService;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.request.PaymentRequest;
import me.tuhin47.orderservice.payload.response.OrderResponse;
import me.tuhin47.orderservice.repository.OrderRepository;
import me.tuhin47.orderservice.service.OrderService;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.payload.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;


    @Override
    public String placeOrder(OrderRequest orderRequest) {

        log.info("OrderServiceImpl | placeOrder is called");

        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED

        log.info("OrderServiceImpl | placeOrder | Placing Order Request orderRequest : " + orderRequest.toString());

        log.debug("OrderServiceImpl | placeOrder | Calling productService through FeignClient");
        String productId = orderRequest.getProductId();

        log.debug("Fetching Product with id" + productId);
        productService.getProductById(productId);

        productService.reduceQuantity(productId, orderRequest.getQuantity());

        log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");

        Order order = placeOrderRequest(orderRequest);

        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("OrderServiceImpl | placeOrder | Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("OrderServiceImpl | placeOrder | Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(String orderId) {

        log.info("OrderServiceImpl | getOrderDetails | Get order details for Order Id : {}", orderId);

        Order order
                = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for the order Id:" + orderId,
                        "NOT_FOUND",
                        404));

        log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}", order.getProductId());
        ProductResponse productResponse = productService.getProductById(order.getProductId()).getBody();

        log.info("OrderServiceImpl | getOrderDetails | Getting payment information form the payment Service");
        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(order.getId()).getBody();

        assert productResponse != null;
        OrderResponse.ProductDetails productDetails
                = OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getId())
                .build();

        assert paymentResponse != null;
        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails
                .builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse orderResponse
                = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        log.info("OrderServiceImpl | getOrderDetails | orderResponse : " + orderResponse.toString());

        return orderResponse;
    }

    @Override
    public Order placeOrderRequest(OrderRequest orderRequest) {
        log.info("OrderServiceImpl | placeOrder | Creating Order with Status CREATED");
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
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
