/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.service.impl;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sns.core.SnsNotification;
import lombok.RequiredArgsConstructor;
import me.tuhin47.awsreactive.common.AmazonProperties;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;
import me.tuhin47.awsreactive.sns.sqs.service.MessagePublishService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagePublishServiceImpl implements MessagePublishService {

    private final SnsTemplate snsTemplate;
    private final AmazonProperties amazonProperties;

    public void publishMessage(PublishEvent publishEvent) {
        snsTemplate.sendNotification(
            amazonProperties.getSns().getTopicName(), SnsNotification.builder(publishEvent).build());
    }
}
