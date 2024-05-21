package io.swagger.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class FakeLogGenerator {

    private static final Logger logger = LoggerFactory.getLogger(FakeLogGenerator.class);
    private static final List<String> USERS = Arrays.asList("john_doe", "jane_smith", "alice_jones", "bob_brown", "charlie_clark");
    private static final List<String> LEVELS = Arrays.asList("INFO", "WARN", "ERROR");
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final List<String> INFO_MESSAGES = Arrays.asList(
            "User %s logged in",
            "User %s logged out",
            "User %s updated profile",
            "User %s accessed resource %s",
            "User %s downloaded file %s"
    );

    private static final List<String> WARN_MESSAGES = Arrays.asList(
            "User %s attempted unauthorized access",
            "High memory usage detected",
            "Potential security threat detected from IP %s",
            "Failed attempt to access restricted resource %s"
    );

    private static final List<String> ERROR_MESSAGES = Arrays.asList(
            "Failed login attempt for user %s from IP %s",
            "Database connection failed",
            "Error processing request from IP %s",
            "System crash detected",
            "Critical error in service %s"
    );

    @Scheduled(fixedRate = 10000) // Ogni 10 secondi
    public void generateLog() {
        String level = getRandomElement(LEVELS);
        String user = getRandomElement(USERS);
        String ip = "192.168.1." + RANDOM.nextInt(256);
        String resource = "/api/resource/" + RANDOM.nextInt(100);
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(FORMATTER);

        String message;
        switch (level) {
            case "WARN":
                message = String.format(getRandomElement(WARN_MESSAGES), user, ip);
                break;
            case "ERROR":
                message = String.format(getRandomElement(ERROR_MESSAGES), user, ip);
                break;
            default:
                message = String.format(getRandomElement(INFO_MESSAGES), user, resource, ip);
                break;
        }

        String logEntry = String.format("%s , %s , %s , IP: %s", formattedDate, level, message, ip);
        logToFile(logEntry);
        //logMessage(level, message);
    }

    private void logToFile(String logEntry) {
        try {
            Path logFile = Paths.get("logs/application.log");
            if (!Files.exists(logFile)) {
                Files.createDirectories(logFile.getParent());
                Files.createFile(logFile);
            }
            Files.write(logFile, (logEntry + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void logMessage(String level, String message) {

        switch (level) {
            case "WARN":
                logger.warn(message);
                break;
            case "ERROR":
                logger.error(message);
                break;
            default:
                logger.info(message);
                break;
        }
    }

    private <T> T getRandomElement(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}