/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.support.NotificationMessageArgumentResolver;
import lombok.Data;
import me.tuhin47.awsreactive.common.AmazonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MessageConverter;

import java.util.Collections;

@Data
@Configuration
public class AwsSqsConfig {

    private final AmazonProperties amazonProperties;

    @Primary
    @Bean(name = "amazonSQSAsync")
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                                          .withCredentials(amazonProperties.getCredentialsProvider())
                                          .withEndpointConfiguration(amazonProperties.getEndpointConfiguration())
                                          .build();
    }

    @Bean(name = "queueMessagingTemplate")
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean(name = "queueMessageHandlerFactory")
    public QueueMessageHandlerFactory queueMessageHandlerFactory(
        MessageConverter messageConverter, AmazonSQSAsync amazonSQSAsync) {
        QueueMessageHandlerFactory queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        NotificationMessageArgumentResolver notificationMessageArgumentResolver =
            new NotificationMessageArgumentResolver(messageConverter);
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync);
        queueMessageHandlerFactory.setArgumentResolvers(
            Collections.singletonList(notificationMessageArgumentResolver));
        return queueMessageHandlerFactory;
    }
}
