package me.tuhin47.orderservice;

import me.tuhin47.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.orderservice", "me.tuhin47.config", "me.tuhin47.entity", "me.tuhin47.jwt"})
@EnableFeignClients(basePackages = "me.tuhin47.client")
@Import(AxonConfig.class)
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


}
