package me.tuhin47.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.payload.response.TopOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "ORDER-SERVICE/order", configuration = FeignConfig.class)
public interface OrderService extends FeignService {

    @Override
    default String getService() {
        return "OrderService";
    }

    @GetMapping("/top-orders-by-date-range")
    ResponseEntity<List<TopOrderDto>> getTop10OrderByDateRange(
        @RequestParam("startDate") Instant startDate,
        @RequestParam("endDate") Instant endDate);


}
