package me.tuhin47.orderservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.tuhin47.orderservice.command.CreateOrderCommand;
import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.request.OrderRequest;
import me.tuhin47.orderservice.payload.response.OrderResponse;
import me.tuhin47.orderservice.service.OrderService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
@Api(tags = "Order API")
public class OrderController {

    private final OrderService orderService;
    private final CommandGateway commandGateway;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/placeorder")
    @ApiOperation("Place an order")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        log.info("OrderController | placeOrder is called");

        log.info("OrderController | placeOrder | orderRequest: {}", orderRequest.toString());

        String orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{orderId}")
    @ApiOperation("Get order details by ID")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable String orderId) {

        log.info("OrderController | getOrderDetails is called");

        OrderResponse orderResponse = orderService.getOrderDetails(orderId);

        log.info("OrderController | getOrderDetails | orderResponse : " + orderResponse.toString());

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRestModel) {

        Order order = orderService.placeOrderRequest(orderRestModel);

        log.info("createOrder() saved with: order = [" + order + "]");

        var orderId = order.getId();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                                                                  .orderId(orderId)
                                                                  .productId(orderRestModel.getProductId())
                                                                  .quantity(orderRestModel.getQuantity())
                                                                  .orderStatus("CREATED")
                                                                  .userId(order.getUpdatedBy())
                                                                  .build();

        commandGateway.sendAndWait(createOrderCommand);

        return orderId;
    }
}
