package me.tuhin47.paymentservice.service;

import me.tuhin47.paymentservice.payload.PaymentRequest;
import me.tuhin47.paymentservice.payload.PaymentResponse;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
