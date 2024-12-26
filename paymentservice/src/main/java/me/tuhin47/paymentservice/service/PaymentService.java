package me.tuhin47.paymentservice.service;

import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;


public interface PaymentService {
    String doPayment(TransactionRequest transactionRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
