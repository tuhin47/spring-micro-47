package me.tuhin47.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.tuhin47.config.exception.apierror.CustomException;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "AUTH-SERVICE/auth", configuration = FeignConfig.class)
public interface UserService {

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam("ids") String[] ids);

    default ResponseEntity<Void> fallback(Exception e) {
        if (e instanceof CustomException) {
            throw ((CustomException) e);
        }
        throw new CustomException("User Service is not available", "UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE.value());
    }

}
