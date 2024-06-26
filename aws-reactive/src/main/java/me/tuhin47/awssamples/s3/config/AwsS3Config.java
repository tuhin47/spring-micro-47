package me.tuhin47.awssamples.s3.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.tuhin47.awssamples.common.AmazonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RequiredArgsConstructor
public class AwsS3Config {

    private final AmazonProperties amazonProperties;

    @Bean(name = "amazonS3")
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(amazonProperties.getCredentialsProvider())
                                    .withEndpointConfiguration(amazonProperties.getEndpointConfiguration())
                                    .build();
    }


}
