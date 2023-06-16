package com.microservice.apigateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@SpringBootApplication
@EnableEurekaClient
@Log4j2
public class ApigatewayApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(
                                CircuitBreakerConfig.ofDefaults()

                        ).build()
        );
    }

    @Bean
    KeyResolver userKeySolver() {
        return exchange -> Mono.just("userKey");
    }

    @Value("${spring.servlet.multipart.location}")
    String tempLocation;

    @Override
    public void run(String... args) {
        if (true) {
            return;
        }
        String tempDir = System.getProperty("java.io.tmpdir");
        var fileName = "Sample.txt";
        var path = Path.of(tempDir, fileName);
        write(path);
        read(path);
    }

    public static void write(Path filePath) {

        try {

            var bufferedWriter = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING);

            log.debug("Write some content to the file");
            bufferedWriter.write("This is the content of the text file.");
            bufferedWriter.newLine();
            log.debug("Move to the next line");

            bufferedWriter.write("You can add more lines as needed.");
            bufferedWriter.newLine();

            log.debug("Close the resources");
            bufferedWriter.close();

            log.info("File created and content written successfully.");
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void read(Path filePath) {

        try {
            BufferedReader bufferedReader = Files.newBufferedReader(filePath);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.info(line);
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
