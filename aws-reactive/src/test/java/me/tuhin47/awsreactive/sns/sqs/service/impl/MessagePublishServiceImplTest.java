package me.tuhin47.awsreactive.sns.sqs.service.impl;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsTemplate;
import me.tuhin47.awsreactive.common.AmazonProperties;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessagePublishServiceImplTest {

    @Test
    void publishesEventAsSnsNotification() {
        SnsTemplate snsTemplate = mock(SnsTemplate.class);
        AmazonProperties amazonProperties = mock(AmazonProperties.class);
        AmazonProperties.SNS snsProperties = mock(AmazonProperties.SNS.class);
        when(amazonProperties.getSns()).thenReturn(snsProperties);
        when(snsProperties.getTopicName()).thenReturn("events");
        PublishEvent publishEvent = new PublishEvent();

        new MessagePublishServiceImpl(snsTemplate, amazonProperties).publishMessage(publishEvent);

        verify(snsTemplate).sendNotification(eq("events"), any(SnsNotification.class));
    }
}