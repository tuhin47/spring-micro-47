package me.tuhin47.apigateway.config;

import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.handler.TracingObservationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Component
@RequiredArgsConstructor
@Slf4j
public class AddResponseHeaderFilter implements WebFilter {

    public static final String TRACE_ID = "trace-id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        response.beforeCommit(() -> populateTraceId(exchange));
        return chain.filter(exchange);
    }

    private Mono<Void> populateTraceId(ServerWebExchange exchange) {
        ServerRequestObservationContext observationContext = exchange.getAttribute(ServerRequestObservationContext.class.getName());

        TracingObservationHandler.TracingContext tracingContext = Objects.requireNonNull(observationContext)
                                                                         .get(TracingObservationHandler.TracingContext.class);

        if (tracingContext != null) {
            TraceContext context = tracingContext.getSpan().context();
            String traceId = context.traceId();
            String spanId = context.spanId();
            log.info("Span : {} Trace : {}", spanId, traceId);
            exchange.getResponse().getHeaders().add(TRACE_ID, traceId);
        } else {
            log.info("Span not found");
        }
        return Mono.empty();
    }
}