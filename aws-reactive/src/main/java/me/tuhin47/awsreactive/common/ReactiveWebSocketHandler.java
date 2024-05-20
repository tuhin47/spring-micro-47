package me.tuhin47.awsreactive.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awsreactive.s3.service.SocketService;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private final SocketService socketService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        onOpen(session);
        return session.send(session.receive()
                                   .doOnNext(message -> {
                                   })
                                   .doOnTerminate(() -> onClose(session))
                                   .map(msg -> session.textMessage("Echo: " + msg.getPayloadAsText())));
    }

    private void onOpen(WebSocketSession session) {
        log.info("Connection opened: " + session.getId());
        socketService.addSession(session);

    }

    private void onClose(WebSocketSession session) {
        log.info("Connection closed: " + session.getId());
        socketService.removeSession(session);
    }

}
