package me.tuhin47.awssamples.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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


    public AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(url, region);
    }

    public AWSStaticCredentialsProvider getCredentialsProvider() {
        return new AWSStaticCredentialsProvider(getBasicAWSCredentials());
    }

    public BasicAWSCredentials getBasicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}
