package me.tuhin47.productservice;

import me.tuhin47.config.RequestDataHolder;
import me.tuhin47.exporter.ExporterUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.annotation.RequestScope;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.productservice", "me.tuhin47.config", "me.tuhin47.entity", "me.tuhin47.jwt"})
@Import(ExporterUtils.class)
@EnableFeignClients(basePackages = "me.tuhin47.client")
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    @Primary
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Product Service API")
                                   .description("API endpoints for managing Product api")
                                   .version("1.0.0")
                                   .build();
    }

    @Bean
    @RequestScope
    public RequestDataHolder requestScopedBean() {
        return new RequestDataHolder();
    }
}
