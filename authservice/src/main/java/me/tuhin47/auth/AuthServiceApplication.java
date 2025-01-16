package me.tuhin47.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import me.tuhin47.config.AxonConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.auth", "me.tuhin47.entity", "me.tuhin47.config", "me.tuhin47.jwt"})
@EnableJpaRepositories
@EnableTransactionManagement
@Import(AxonConfig.class)
@EnableFeignClients(basePackages = "me.tuhin47.client")
@OpenAPIDefinition(
    info = @Info(
        title = "Auth Service",
        version = "1.0.0",
        description = "API endpoints for managing Auth api"
    )
)
public class AuthServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplicationBuilder app = new SpringApplicationBuilder(AuthServiceApplication.class);
        app.run();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuthServiceApplication.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    
}
