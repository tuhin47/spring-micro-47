package me.tuhin47.paymentservice.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.paymentservice.controller.PaymentController;
import me.tuhin47.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<String> doPayment(@RequestBody @Valid TransactionRequest transactionRequest) {

        return new ResponseEntity<>(
            paymentService.doPayment(transactionRequest),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId) {

        return new ResponseEntity<>(
            paymentService.getPaymentDetailsByOrderId(orderId),
            HttpStatus.OK
        );
    }
}
