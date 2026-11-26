/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.service.impl;

import io.awspring.cloud.sqs.annotation.SnsNotificationMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.SqsHeaders;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;
import me.tuhin47.awsreactive.sns.sqs.service.MessageConsumerService;
import me.tuhin47.awsreactive.sns.sqs.storage.PublishEventStorage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class MessageConsumerServiceImpl implements MessageConsumerService {

    @Override
    @SqsListener(value = "${config.aws.sqs.queue-name}")
    public void consumeMessage(
        @SnsNotificationMessage PublishEvent publishEvent,
        @Header(SqsHeaders.SQS_RAW_MESSAGE_ID_HEADER) String publishEventId) {
        if (Objects.nonNull(publishEvent)) {
            log.info("Publish event consumed: {}", publishEvent);
            PublishEventStorage.addPublishEventToStorage(publishEventId, publishEvent);
        }
    }
}
