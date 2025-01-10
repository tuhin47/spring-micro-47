package me.tuhin47.paymentservice.controller;

import io.swagger.annotations.*;
import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "Payment API", tags = "PAYMENT-API", description = "Operations related to Payments")
public interface PaymentController {

    @ApiOperation(value = "Process a payment", notes = "Creates a new payment transaction")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Payment processed successfully"),
        @ApiResponse(code = 400, message = "Invalid payment request")
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    ResponseEntity<String> doPayment(@RequestBody TransactionRequest transactionRequest);


    @ApiOperation(value = "Get payment details by order ID", authorizations = @Authorization("USER"),
        notes = "Retrieves payment details for a given order ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Payment details retrieved successfully"),
        @ApiResponse(code = 404, message = "Payment details not found for the given order ID")
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId);
}
