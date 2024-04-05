/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awssamples.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class HealthRouter {

    @Bean
    public RouterFunction<ServerResponse> healthRouterFunction(HealthHandler healthHandler) {
        RequestPredicate healthRoute =
            RequestPredicates.GET("/aws/health")
                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        return RouterFunctions.route(healthRoute, healthHandler::health);
    }
}
