package me.tuhin47.apigateway.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Slf4j
public class AddResponseHeaderFilter implements WebFilter {

    public static final String TRACE_ID = "trace-id";
    public static final String SPAN_ID = "span-id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        response.beforeCommit(() -> {
            Span span = exchange.getAttribute(Span.class.getName());
            if (span != null) {
                String traceId = span.context().traceId();
                String spanId = span.context().spanId();
                log.info("Span : {} Trace : {}", spanId, traceId);
                exchange.getResponse().getHeaders().add(TRACE_ID, traceId);
                exchange.getResponse().getHeaders().add(SPAN_ID, spanId);
            } else {
                log.info("Span not found");
            }
            return Mono.empty();
        });
        return chain.filter(exchange);
    }
}