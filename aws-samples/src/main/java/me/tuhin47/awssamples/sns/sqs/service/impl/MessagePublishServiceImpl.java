/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awssamples.sns.sqs.service.impl;

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.RequiredArgsConstructor;
import me.tuhin47.awssamples.common.AmazonProperties;
import me.tuhin47.awssamples.sns.sqs.model.event.PublishEvent;
import me.tuhin47.awssamples.sns.sqs.service.MessagePublishService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagePublishServiceImpl implements MessagePublishService {

    private final NotificationMessagingTemplate notificationMessagingTemplate;
    private final AmazonProperties amazonProperties;

    public void publishMessage(PublishEvent publishEvent) {
        notificationMessagingTemplate.convertAndSend(amazonProperties.getSns().getTopicName(), publishEvent);
    }
}
