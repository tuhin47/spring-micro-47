package me.tuhin47.orderservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.annotations.CurrentUser;
import me.tuhin47.config.redis.UserRedis;
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

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
@Api(tags = "Order API")
public class OrderController {

    private final OrderService orderService;
    private final CommandGateway commandGateway;


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{orderId}")
    @ApiOperation("Get order details by ID")
    public ResponseEntity<OrderResponseWithDetails> getOrderDetails(@PathVariable String orderId) {

        log.info("OrderController | getOrderDetails is called");

        OrderResponseWithDetails orderResponse = orderService.getOrderDetails(orderId);

        log.info("OrderController | getOrderDetails | orderResponse : " + orderResponse.toString());

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequest orderRestModel, @CurrentUser UserRedis userRedis) {

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

        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/top-orders-by-date-range")
    public ResponseEntity<List<TopOrderDto>> getTop10OrderByDateRange(
        @RequestParam(value = "startDate", required = false) Instant startDate,
        @RequestParam(value = "endDate", required = false) Instant endDate) {

        return new ResponseEntity<>(orderService.getTop10OrderByDateRange(startDate, endDate), HttpStatus.OK);
    }
}
