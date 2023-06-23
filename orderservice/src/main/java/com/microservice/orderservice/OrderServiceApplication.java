package com.microservice.orderservice;

import com.microservice.orderservice.config.FeignConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.microservice.orderservice","me.tuhin47.config","me.tuhin47.jwt"})
@EnableFeignClients
public class OrderServiceApplication
{
    
    public static void main (String[] args)
    {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
    
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate ()
    {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) ->
        {
            FeignConfig.addAuthorizationToken(request.getHeaders());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
    
}
