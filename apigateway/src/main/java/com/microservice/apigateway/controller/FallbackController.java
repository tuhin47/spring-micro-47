package com.microservice.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping ("/orderServiceFallBack")
    public String orderServiceFallback() {
        return "Order Service is down!";
    }

    @RequestMapping("/paymentServiceFallBack")
    public String paymentServiceFallback() {
        return "Payment Service is down!";
    }

    @RequestMapping("/productServiceFallBack")
    public String productServiceFallback() {
        return "Product Service is down!";
    }

    @RequestMapping("/authServiceFallBack")
    public String authServiceFallback() {
        return "Auth Service is down!";
    }

    @RequestMapping("/chatServiceFallBack")
    public String chatServiceFallback() {
        return "Chat Service is down!";
    }

}
