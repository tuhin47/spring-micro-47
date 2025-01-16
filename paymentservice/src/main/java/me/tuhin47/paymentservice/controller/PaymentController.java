package me.tuhin47.paymentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Payment API", description = "Operations related to Payments")
public interface PaymentController {

    @Operation(
        summary = "Process a payment",
        description = "Creates a new payment transaction",
        security = @SecurityRequirement(name = "USER")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid payment request",
            content = @Content(schema = @Schema(hidden = true)))
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    ResponseEntity<String> doPayment(@RequestBody TransactionRequest transactionRequest);

    @Operation(
        summary = "Get payment details by order ID",
        description = "Retrieves payment details for a given order ID",
        security = @SecurityRequirement(name = "USER")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment details retrieved successfully",
            content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "404", description = "Payment details not found for the given order ID",
            content = @Content(schema = @Schema(hidden = true)))
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId);
}
