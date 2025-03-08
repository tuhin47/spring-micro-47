package me.tuhin47.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class CsrfCookieWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty())
                       .doOnSuccess(token -> {
                           log.info("token {}", token);
                           if (token instanceof CsrfToken csrfToken) {
                               csrfToken.getToken();
                           }
                       })
                       .then(chain.filter(exchange));
    }
}