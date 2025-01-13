package me.tuhin47.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "AUTH-SERVICE", configuration = FeignConfig.class)
public interface UserService extends FeignService {

    @GetMapping("/auth/users")
    ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(value = "ids", required = false) String[] ids);

    @Override
    default String getService() {
        return "UserService";
    }
}
