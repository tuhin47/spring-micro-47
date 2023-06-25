package me.tuhin47.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.config","me.tuhin47.jwt"})
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	@Bean
	@Primary
	public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Product Service API")
                .description("API endpoints for managing Product api")
                .version("1.0.0")
                .build();
    }
}
