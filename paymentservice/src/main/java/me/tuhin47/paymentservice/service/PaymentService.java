package me.tuhin47.paymentservice.service;

import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.paymentservice.payload.PaymentRequest;


public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
