package me.tuhin47.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.orderservice.command.CreateOrderCommand;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponseWithDetails;
import me.tuhin47.orderservice.service.OrderService;
import me.tuhin47.payload.response.TopOrderDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Order API", description = "Endpoints for order management")
public class OrderController {

    private final OrderService orderService;
    private final CommandGateway commandGateway;

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @GetMapping("/{orderId}")
    @Operation(summary = "Get order details by ID")
    public ResponseEntity<OrderResponseWithDetails> getOrderDetails(@PathVariable String orderId) {
        log.info("Fetching order details for orderId={}", orderId);
        OrderResponseWithDetails orderResponse = orderService.getOrderDetails(orderId);
        log.info("Order details fetched: {}", orderResponse);
        return ResponseEntity.ok(orderResponse);
    }

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        log.info("Creating order for request: {}", orderRequest);

        Order order = orderService.placeOrderRequest(orderRequest);
        log.info("Order saved: {}", order);

        var orderId = order.getId();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                                                                  .orderId(orderId)
                                                                  .productId(orderRequest.getProductId())
                                                                  .quantity(orderRequest.getQuantity())
                                                                  .orderStatus("CREATED")
                                                                  .userId(order.getUpdatedBy())
                                                                  .build();

        commandGateway.sendAndWait(createOrderCommand);
        log.info("Order creation command sent: {}", createOrderCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @GetMapping("/top-orders-by-date-range")
    @Operation(summary = "Get top 10 orders within a date range")
    public ResponseEntity<List<TopOrderDto>> getTop10OrderByDateRange(
        @RequestParam(value = "startDate", required = false) Instant startDate,
        @RequestParam(value = "endDate", required = false) Instant endDate) {

        log.info("Fetching top orders between startDate={} and endDate={}", startDate, endDate);
        List<TopOrderDto> topOrders = orderService.getTop10OrderByDateRange(startDate, endDate);
        log.info("Top orders fetched: {}", topOrders);

        return ResponseEntity.ok(topOrders);
    }
}
