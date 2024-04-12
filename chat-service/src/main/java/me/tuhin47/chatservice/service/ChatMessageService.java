package me.tuhin47.chatservice.service;

import lombok.RequiredArgsConstructor;
import me.tuhin47.chatservice.exception.ResourceNotFoundException;
import me.tuhin47.chatservice.model.ChatMessage;
import me.tuhin47.chatservice.model.MessageStatus;
import me.tuhin47.chatservice.repository.ChatMessageRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;
    private final MongoOperations mongoOperations;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
            senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);
        var messages = chatId.map(repository::findByChatId).orElse(new ArrayList<>());

        if (!messages.isEmpty()) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessage findById(String id) {
        return repository
            .findById(id)
            .map(chatMessage -> {
                chatMessage.setStatus(MessageStatus.DELIVERED);
                return repository.save(chatMessage);
            })
            .orElseThrow(() ->
                new ResourceNotFoundException("can't find message (" + id + ")"));
    }

    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        Query query = new Query(
            Criteria
                .where("senderId").is(senderId)
                .and("recipientId").is(recipientId));
        Update update = Update.update("status", status);
        mongoOperations.updateMulti(query, update, ChatMessage.class);
    }
}
