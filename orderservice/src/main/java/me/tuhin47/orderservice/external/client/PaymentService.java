package me.tuhin47.orderservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.orderservice.config.FeignConfig;
import me.tuhin47.orderservice.exception.CustomException;
import me.tuhin47.orderservice.payload.request.PaymentRequest;
import me.tuhin47.orderservice.payload.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment", configuration = FeignConfig.class)
public interface PaymentService
{
    
    @PostMapping
    ResponseEntity<Long> doPayment (@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/order/{orderId}")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId);

    default ResponseEntity<Long> fallback (Exception e)
    {
        if (e instanceof CustomException) {
            throw ((CustomException) e);
        }

        throw new CustomException("Payment Service is not available",
                "UNAVAILABLE",
                500);
    }
}
