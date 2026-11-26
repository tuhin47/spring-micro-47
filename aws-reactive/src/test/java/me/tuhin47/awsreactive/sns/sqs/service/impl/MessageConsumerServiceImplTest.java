package me.tuhin47.awsreactive.sns.sqs.service.impl;

import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;
import me.tuhin47.awsreactive.sns.sqs.storage.PublishEventStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class MessageConsumerServiceImplTest {

    @Test
    void storesReceivedEventByQueueMessageId() {
        PublishEvent event = new PublishEvent();

        new MessageConsumerServiceImpl().consumeMessage(event, "message-1");

        assertSame(event, PublishEventStorage.getAndRemovePublishEventFromStorage("message-1"));
    }

    @Test
    void ignoresNullNotifications() {
        new MessageConsumerServiceImpl().consumeMessage(null, "message-2");

        assertNull(PublishEventStorage.getAndRemovePublishEventFromStorage("message-2"));
    }
}