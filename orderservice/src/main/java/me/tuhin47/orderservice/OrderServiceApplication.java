package me.tuhin47.orderservice;

import me.tuhin47.config.AxonConfig;
import me.tuhin47.orderservice.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.orderservice", "me.tuhin47.config", "me.tuhin47.jwt"})
@EnableFeignClients
@Import({ AxonConfig.class })
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    @Primary
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Order Service")
                .description("API endpoints for managing Order api")
                .version("1.0.0")
                .build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) ->
        {
            FeignConfig.addAuthorizationToken(request.getHeaders());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
