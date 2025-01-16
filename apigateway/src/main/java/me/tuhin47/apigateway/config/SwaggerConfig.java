package me.tuhin47.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
                .title("API Gateway")
                .version("1.0")
                .description("API Gateway for Microservices")
                .contact(new Contact().name("MD Towhidul Islam")));
    }

    @Bean
    public Set<SwaggerUrl> apis(SwaggerUiConfigProperties swaggerUiConfig, RouteLocator routeLocator) {
        final Set<SwaggerUrl> swaggerUrlSet = new HashSet<>();
        routeLocator.getRoutes().subscribe(route -> {
            String name = route.getId().split("-")[0];
            String url = "/" + name.toLowerCase() + "/v3/api-docs";
            var wsResource = new SwaggerUrl(name, url, name);
            swaggerUrlSet.add(wsResource);
        });
        swaggerUiConfig.setUrls(swaggerUrlSet);
        return swaggerUrlSet;
    }
}
