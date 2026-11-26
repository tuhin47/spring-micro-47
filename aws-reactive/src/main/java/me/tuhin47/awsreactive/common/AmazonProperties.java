package me.tuhin47.awsreactive.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "config.aws")
public class AmazonProperties {

    private String region;
    private String url;
    private String accessKey;
    private String secretKey;

    private final S3 s3 = new S3();
    private final SNS sns = new SNS();
    private final SQS sqs = new SQS();

    @Getter
    @Setter
    public static class S3 {
        private String bucketName;
    }

    @Getter
    @Setter
    public static class SNS {
        private String topicName;
    }

    @Getter
    @Setter
    public static class SQS {
        private String queueName;
    }


    public URI getEndpointUri() {
        return URI.create(url);
    }

    public Region getAwsRegion() {
        return Region.of(region);
    }

    public AwsCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }
}
