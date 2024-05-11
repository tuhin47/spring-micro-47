package me.tuhin47.awssamples.s3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketService {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) {
        String key = getKey(session);
        sessions.put(key, session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(getKey(session));
    }

    public void sendMessageToAll(String message) {
        sessions.values().forEach(session ->
            session.send(Mono.just(session.textMessage(message))).subscribe()
        );
    }

    public void sendMessageTo(String id, String message) {
        WebSocketSession webSocketSession = sessions.get(id);
        if (webSocketSession != null) {
            webSocketSession.send(Mono.just(webSocketSession.textMessage(message))).subscribe();
        }
    }

    private static String getKey(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        String path = uri.getPath();
        if (path.contains("uploaded") && path.lastIndexOf('/') != -1) {
            return path.substring(path.lastIndexOf('/') + 1);
        }

        return session.getId();
    }
}
