package me.tuhin47.client;

import me.tuhin47.config.exception.apierror.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface FeignService {
    String getService();

    default ResponseEntity<Void> fallback(Throwable e) {
        if (e instanceof CustomException) {
            throw (CustomException) e;
        }
        throw new CustomException(getService() + " issue " + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.toString(), HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
