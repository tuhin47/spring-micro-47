package me.tuhin47.awsreactive.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

@Component
@Slf4j
public class AWSInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        main(args);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] copyReadme = {"cp", "README.md", "/tmp/"};
        executeCommand(copyReadme);
        String[] s3Bucket = {"docker", "exec", "localstack", "sh", "/docker-entrypoint-initaws.d/init-s3-bucket.sh"};
        executeCommand(s3Bucket);
        String[] sqsSnwBucket = {"docker", "exec", "localstack", "sh", "/docker-entrypoint-initaws.d/init-sns-sqs.sh"};
        executeCommand(sqsSnwBucket);
    }

    private static void executeCommand(String... command) throws IOException, InterruptedException {
        log.info("Executing command" + Arrays.toString(command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Combine stdout and stderr
        Process process = processBuilder.start();

        // Capture and print the output
        Scanner scanner = new Scanner(process.getInputStream());
        while (scanner.hasNextLine()) {
            log.info(scanner.nextLine());
        }
        scanner.close();

        process.waitFor(); // Wait for the process to finish
        int exitCode = process.exitValue();

        if (exitCode != 0) {
            log.error("Error initializing S3 bucket. Exit code: " + exitCode);
        }
        log.info("Execution completed");
    }
}
