package me.tuhin47.awsreactive.s3.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.awsreactive.common.AmazonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

    private final AmazonProperties amazonProperties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                       .credentialsProvider(amazonProperties.getCredentialsProvider())
                       .endpointOverride(amazonProperties.getEndpointUri())
                       .region(amazonProperties.getAwsRegion())
                       .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                       .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.builder()
                            .credentialsProvider(amazonProperties.getCredentialsProvider())
                            .endpointOverride(amazonProperties.getEndpointUri())
                            .region(amazonProperties.getAwsRegion())
                            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                            .build();
    }

    @Bean
    public S3TransferManager transferManager(S3AsyncClient s3AsyncClient) {
        return S3TransferManager.builder().s3Client(s3AsyncClient).build();
    }
}
