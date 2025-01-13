package me.tuhin47.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment", configuration = FeignConfig.class)
public interface PaymentService extends FeignService {

    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody TransactionRequest transactionRequest);

    @GetMapping("/order/{orderId}")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId);

    @Override
    default String getService() {
        return "PaymentService";
    }

}
