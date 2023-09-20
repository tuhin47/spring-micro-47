package me.tuhin47.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping ("/orderServiceFallBack")
    public ResponseEntity<String> orderServiceFallback() {
        return new ResponseEntity<>("Order Service is down!", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/paymentServiceFallBack")
    public ResponseEntity<String> paymentServiceFallback() {
        return new ResponseEntity<>("Payment Service is down!", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/productServiceFallBack")
    public ResponseEntity<String> productServiceFallback() {
        return new ResponseEntity<>("Product Service is down!", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/authServiceFallBack")
    public ResponseEntity<String> authServiceFallback() {
        return new ResponseEntity<>("Auth Service is down!", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/chatServiceFallBack")
    public ResponseEntity<String> chatServiceFallback() {
        return new ResponseEntity<>("Chat Service is down!", HttpStatus.SERVICE_UNAVAILABLE);
    }

}
