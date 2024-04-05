/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awssamples.sns.sqs.service.impl;

import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awssamples.sns.sqs.model.event.PublishEvent;
import me.tuhin47.awssamples.sns.sqs.service.MessageConsumerService;
import me.tuhin47.awssamples.sns.sqs.storage.PublishEventStorage;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class MessageConsumerServiceImpl implements MessageConsumerService {

    @Override
    @SqsListener(
        value = "${config.aws.sqs.queue-name}",
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consumeMessage(
        @NotificationMessage PublishEvent publishEvent, String publishEventId) {
        if (Objects.nonNull(publishEvent)) {
            log.info("Publish event consumed: {}", publishEvent);
            PublishEventStorage.addPublishEventToStorage(publishEventId, publishEvent);
        }
    }
}
