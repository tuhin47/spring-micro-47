package me.tuhin47.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.config.FeignConfig;
import me.tuhin47.exception.CustomException;
import me.tuhin47.payload.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
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
        @PathVariable("id") String productId,
        @RequestParam long quantity
    );

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String productId);

    default ResponseEntity<Void> fallback(Exception e) {
        if (e instanceof CustomException) {
            throw ((CustomException) e);
        }
        throw new CustomException("Product Service is not available",
            "UNAVAILABLE",
            HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
