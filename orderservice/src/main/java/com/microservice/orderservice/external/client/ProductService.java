package com.microservice.orderservice.external.client;

import com.microservice.orderservice.config.FeignConfig;
import com.microservice.orderservice.exception.CustomException;
import com.microservice.orderservice.payload.response.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product", configuration = FeignConfig.class)
public interface ProductService {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    );
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId);

    default ResponseEntity<Void> fallback(Exception e) {
        if (e instanceof CustomException) {
            throw ((CustomException) e);
        }
        throw new CustomException("Product Service is not available",
                "UNAVAILABLE",
                500);
    }
}
