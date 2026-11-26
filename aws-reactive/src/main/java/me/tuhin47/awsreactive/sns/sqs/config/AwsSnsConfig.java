/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.awsreactive.common.AmazonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@RequiredArgsConstructor
public class AwsSnsConfig {

    private final AmazonProperties amazonProperties;

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                        .credentialsProvider(amazonProperties.getCredentialsProvider())
                        .endpointOverride(amazonProperties.getEndpointUri())
                        .region(amazonProperties.getAwsRegion())
                        .build();
    }

}
