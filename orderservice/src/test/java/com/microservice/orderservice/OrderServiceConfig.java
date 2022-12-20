package com.microservice.orderservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OrderServiceConfig {

    @Bean
    public ServiceInstanceListSupplier supplier() {
        return new TestServiceInstanceListSupplier();
    }
}
