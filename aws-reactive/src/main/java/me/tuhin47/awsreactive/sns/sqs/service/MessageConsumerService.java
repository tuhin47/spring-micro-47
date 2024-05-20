/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.service;

import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;

public interface MessageConsumerService {

    void consumeMessage(@NotificationMessage PublishEvent publishEvent, String publishEventId);
}
