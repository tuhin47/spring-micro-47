package me.tuhin47.productservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import me.tuhin47.config.RequestDataHolder;
import me.tuhin47.exporter.ExporterUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.productservice", "me.tuhin47.config", "me.tuhin47.entity", "me.tuhin47.jwt"})
@Import(ExporterUtils.class)
@EnableFeignClients(basePackages = "me.tuhin47.client")
@OpenAPIDefinition(
    info = @Info(
        title = "Product Service API",
        version = "1.0.0",
        description = "API endpoints for managing Product api"
    )
)
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    @RequestScope
    public RequestDataHolder requestScopedBean() {
        return new RequestDataHolder();
    }
}
