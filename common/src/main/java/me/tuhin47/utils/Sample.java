package me.tuhin47.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Log4j2
public class Sample implements CommandLineRunner {

    @Override
    public void run(String... args) {
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
