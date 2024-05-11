package me.tuhin47.awssamples.common;

import me.tuhin47.awssamples.s3.service.SocketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping webSocketMapping(ReactiveWebSocketHandler webSocketHandler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/uploaded/**", webSocketHandler);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(-1);
        return mapping;
    }

    @Bean
    public ReactiveWebSocketHandler webSocketHandler(SocketService socketService) {
        return new ReactiveWebSocketHandler(socketService);
    }

}
