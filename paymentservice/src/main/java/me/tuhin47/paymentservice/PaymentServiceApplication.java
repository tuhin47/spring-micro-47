package me.tuhin47.paymentservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import me.tuhin47.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.paymentservice", "me.tuhin47.config", "me.tuhin47.entity", "me.tuhin47.jwt"})
@EnableFeignClients(basePackages = "me.tuhin47.client")
@Import({AxonConfig.class})
@OpenAPIDefinition(
    info = @Info(
        title = "Payment Service",
        version = "1.0.0",
        description = "API endpoints for managing Payment api"
    )
)
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
