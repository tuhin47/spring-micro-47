package me.tuhin47.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.paymentservice","me.tuhin47.config","me.tuhin47.jwt"})
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Bean
	@Primary
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Payment Service")
				.description("API endpoints for managing Payment api")
				.version("1.0.0")
				.build();
	}
}
