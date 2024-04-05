package me.tuhin47.awssamples.s3.api.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class HealthHandler {

    @NonNull
    public Mono<ServerResponse> health(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                   .body(Mono.just("{\"status\": \"S3 - Healthy!\"}"), String.class);
    }
}
