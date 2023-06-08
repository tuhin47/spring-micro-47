package com.microservice.paymentservice.controller;

import com.microservice.paymentservice.payload.PaymentRequest;
import com.microservice.paymentservice.payload.PaymentResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "Payment API", tags = "PAYMENT-API" ,description = "Operations related to Payments")
public interface PaymentController {

    @ApiOperation(value = "Process a payment", notes = "Creates a new payment transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment processed successfully"),
            @ApiResponse(code = 400, message = "Invalid payment request")
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);


    @ApiOperation(value = "Get payment details by order ID",  authorizations = @Authorization("USER") ,
            notes = "Retrieves payment details for a given order ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment details retrieved successfully"),
            @ApiResponse(code = 404, message = "Payment details not found for the given order ID")
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/order/{orderId}")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId);
}
