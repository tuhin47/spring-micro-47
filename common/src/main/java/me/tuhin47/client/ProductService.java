package me.tuhin47.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.payload.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product", configuration = FeignConfig.class)
public interface ProductService extends FeignService {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
        @PathVariable("id") String productId,
        @RequestParam long quantity
    );

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String productId);

    @Override
    default String getService() {
        return "ProductService";
    }
}
