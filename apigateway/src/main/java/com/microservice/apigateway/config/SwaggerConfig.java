package com.microservice.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Primary
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig implements SwaggerResourcesProvider {

  private final RouteLocator routeLocator;

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();

    routeLocator.getRoutes().subscribe(route -> {
      String name = route.getId().split("-")[0];
      resources.add(swaggerResource(name, "/" + name.toLowerCase() + "/v3/api-docs", "1.0"));
    });

    return resources;
  }

  private SwaggerResource swaggerResource(final String name, final String location,
      final String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
