package me.tuhin47.apigateway.config;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AlwaysCreateSessionSecurityContextRepository implements ServerSecurityContextRepository {

    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return exchange.getSession().flatMap(session -> {
            session.getAttributes().put(SPRING_SECURITY_CONTEXT, context);
            return Mono.empty();
        });
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return exchange.getSession()
                       .flatMap(session -> {
                           if (!session.isStarted()) {
                               session.start(); // Always create a session
                           }
                           return Mono.justOrEmpty(session.getAttribute("SPRING_SECURITY_CONTEXT"));
                       });
    }
}
