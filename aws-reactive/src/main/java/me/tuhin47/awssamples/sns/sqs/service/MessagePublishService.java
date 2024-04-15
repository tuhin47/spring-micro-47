/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awssamples.sns.sqs.service;

import me.tuhin47.awssamples.sns.sqs.model.event.PublishEvent;

public interface MessagePublishService {

    void publishMessage(PublishEvent publishEvent);
}
