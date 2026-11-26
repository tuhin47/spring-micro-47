/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.service;

import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;

public interface MessageConsumerService {

    void consumeMessage(PublishEvent publishEvent, String publishEventId);
}
