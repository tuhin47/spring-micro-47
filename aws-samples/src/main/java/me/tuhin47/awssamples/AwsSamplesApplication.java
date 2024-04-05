package me.tuhin47.awssamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    exclude = {
        io.awspring.cloud.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
        io.awspring.cloud.autoconfigure.context.ContextStackAutoConfiguration.class,
        io.awspring.cloud.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
    })
public class AwsSamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsSamplesApplication.class, args);
    }

}
