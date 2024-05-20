/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import io.awspring.cloud.core.env.ResourceIdResolver;
import io.awspring.cloud.core.env.StackResourceRegistryDetectingResourceIdResolver;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.tuhin47.awsreactive.common.AmazonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MessageConverter;

@Data
@Configuration
@RequiredArgsConstructor
public class AwsSnsConfig {

    private final AmazonProperties amazonProperties;

    @Primary
    @Bean(name = "amazonSNS")
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
                                     .withCredentials(amazonProperties.getCredentialsProvider())
                                     .withEndpointConfiguration(amazonProperties.getEndpointConfiguration())
                                     .build();
    }

    @Bean(name = "notificationMessagingTemplate")
    public NotificationMessagingTemplate notificationMessagingTemplate(
        AmazonSNS amazonSNS, MessageConverter messageConverter) {
        ResourceIdResolver resourceIdResolver = new StackResourceRegistryDetectingResourceIdResolver();
        return new NotificationMessagingTemplate(amazonSNS, resourceIdResolver, messageConverter);
    }

}
